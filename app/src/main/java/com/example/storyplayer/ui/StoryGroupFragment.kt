package com.example.storyplayer.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.storyplayer.R
import com.example.storyplayer.data.model.StoryGroupModel
import com.example.storyplayer.data.model.StoryType
import com.example.storyplayer.databinding.FragmentUserStoriesBinding
import com.example.storyplayer.event.*
import com.example.storyplayer.ui.adapter.StoriesAdapter
import com.example.storyplayer.ui.component.storyprogressview.StoryProgressView
import com.example.storyplayer.ui.custom.ScrollDisabledLinearLayoutManager
import com.example.storyplayer.util.Constants
import com.example.storyplayer.util.Constants.ARG_POSITION
import com.example.storyplayer.util.Constants.ARG_STORY_GROUP
import com.example.storyplayer.util.extension.notNull
import com.example.storyplayer.util.extension.withArgs
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class StoryGroupFragment : Fragment() {

    companion object {

        fun newInstance(storyGroup: StoryGroupModel.StoryGroup, position: Int) =
            StoryGroupFragment().withArgs {
                putParcelable(ARG_STORY_GROUP, storyGroup)
                putInt(ARG_POSITION, position)
            }
    }

    private lateinit var storyGroup: StoryGroupModel.StoryGroup
    private var _binding: FragmentUserStoriesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserStoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        storyGroup = arguments?.get(ARG_STORY_GROUP) as StoryGroupModel.StoryGroup
        Glide.with(requireContext()).load(storyGroup.profileImageUrl).apply(
            RequestOptions().circleCrop().error(R.drawable.ic_launcher_background)
        ).into(binding.imageProfile)
        binding.userName.text = storyGroup.username
    }

    override fun onResume() {
        super.onResume()
        EventBus.getDefault().register(this)
        binding.storyProgressView.apply {
            clear()
            setSize(storyGroup.stories.size)
            setDuration(Constants.STORY_DURATION)
            setCallbacks(object : StoryProgressView.Callbacks {
                override fun onNext() {
                    val layoutManager =
                        (binding.rvStories.layoutManager as ScrollDisabledLinearLayoutManager)
                    val currentPosition = layoutManager.findFirstCompletelyVisibleItemPosition()
                    if (currentPosition + 1 > storyGroup.stories.size - 1) {
                        EventBus.getDefault().post(StoryEvent(StoryEventType.NEXT, currentPosition))
                        return
                    }
                    layoutManager.scrollToPosition(currentPosition + 1)
                }

                override fun onPrevious() {
                    val layoutManager =
                        (binding.rvStories.layoutManager as ScrollDisabledLinearLayoutManager)
                    val currentPosition = layoutManager.findFirstCompletelyVisibleItemPosition()
                    if (currentPosition - 1 < 0) {
                        EventBus.getDefault()
                            .post(StoryEvent(StoryEventType.PREVIOUS, currentPosition))
                        return
                    }
                    layoutManager.scrollToPosition(currentPosition - 1)
                }

                override fun onComplete() {
                    val layoutManager =
                        (binding.rvStories.layoutManager as ScrollDisabledLinearLayoutManager)
                    val currentPosition = layoutManager.findFirstCompletelyVisibleItemPosition()
                    if (currentPosition + 1 > storyGroup.stories.size - 1) {
                        EventBus.getDefault()
                            .post(StoryGroupEvent(StoryGroupEventType.COMPLETE_NEXT))
                        return
                    }
                    layoutManager.scrollToPosition(currentPosition + 1)
                }
            })
            play()
        }
        binding.rvStories.apply {
            layoutManager = ScrollDisabledLinearLayoutManager(context)
            adapter = StoriesAdapter(storyGroup.stories)
        }
    }

    override fun onPause() {
        super.onPause()
        binding.storyProgressView.pause()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe
    fun onDurationChangedEvent(event: DurationChangedEvent) {
        event.duration.notNull {
            binding.storyProgressView.setDurationForIndex(it, event.position)
        }
    }

    @Subscribe
    fun onStoryEvent(event: StoryEvent) {
        when (event.type) {
            StoryEventType.RESUME -> {
                binding.storyProgressView.resume()
            }
            StoryEventType.PAUSE -> {
                binding.storyProgressView.pause()
            }
            StoryEventType.PREVIOUS -> {
                binding.storyProgressView.reverse()
                if (event.adapterPosition == 0) {
                    EventBus.getDefault()
                        .post(StoryGroupEvent(StoryGroupEventType.COMPLETE_PREVIOUS))
                    return
                }
                binding.rvStories.layoutManager?.scrollToPosition(event.adapterPosition - 1)
                binding.rvStories.adapter?.notifyDataSetChanged()
            }
            StoryEventType.COMPLETE,
            StoryEventType.NEXT -> {
                binding.storyProgressView.skip()
                if (event.adapterPosition == storyGroup.stories.size - 1) {
                    EventBus.getDefault().post(StoryGroupEvent(StoryGroupEventType.COMPLETE_NEXT))
                    return
                }
                binding.rvStories.layoutManager?.scrollToPosition(event.adapterPosition + 1)
                binding.rvStories.adapter?.notifyDataSetChanged()
            }
            StoryEventType.CLOSE -> {
                binding.storyProgressView.destroy()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        EventBus.getDefault().unregister(this)
    }
}
