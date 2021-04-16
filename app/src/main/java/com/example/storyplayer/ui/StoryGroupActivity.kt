package com.example.storyplayer.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.storyplayer.data.model.StoryGroupModel
import com.example.storyplayer.databinding.ActivityStoryGroupBinding
import com.example.storyplayer.event.StoryEvent
import com.example.storyplayer.event.StoryEventType
import com.example.storyplayer.event.StoryGroupEvent
import com.example.storyplayer.event.StoryGroupEventType
import com.example.storyplayer.helper.CubeTransformer
import com.example.storyplayer.ui.adapter.StoryGroupPagerAdapter
import com.example.storyplayer.ui.custom.StoryViewPager
import com.example.storyplayer.util.Constants.EXTRA_POSITION
import com.example.storyplayer.util.Constants.EXTRA_STORY_GROUP_MODEL
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class StoryGroupActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStoryGroupBinding
    private lateinit var storyGroupModel: StoryGroupModel
    private var position: Int = 0

    companion object {

        fun newIntent(context: Context, storyGroupModel: StoryGroupModel, position: Int): Intent {
            return Intent(context, StoryGroupActivity::class.java).apply {
                putExtra(EXTRA_STORY_GROUP_MODEL, storyGroupModel)
                putExtra(EXTRA_POSITION, position)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryGroupBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        storyGroupModel = intent.getParcelableExtra(EXTRA_STORY_GROUP_MODEL)!!
        position = intent.getIntExtra(EXTRA_POSITION, 0)
        setupViewPager()
    }

    private fun setupViewPager() {
        binding.viewPagerStories.apply {
            setOnSwipeListener(object : StoryViewPager.OnSwipeListener {
                override fun onSwipeOutAtStart() {
                    finish()
                }

                override fun onSwipeOutAtEnd() {
                    finish()
                }
            })
            offscreenPageLimit = 0
            setPageTransformer(false, CubeTransformer())
            adapter = StoryGroupPagerAdapter(supportFragmentManager, storyGroupModel.storyGroups)
            currentItem = position
            this.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

                override fun onPageSelected(position: Int) {}

                override fun onPageScrollStateChanged(state: Int) {
                    if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                        EventBus.getDefault().post(StoryEvent(StoryEventType.PAUSE, 0))
                    } else if (state == ViewPager.SCROLL_STATE_IDLE) {
                        EventBus.getDefault().post(StoryEvent(StoryEventType.RESUME, 0))
                    }
                }
            })
        }
    }

    @Subscribe
    fun onStoryGroupEvent(event: StoryGroupEvent) {
        when (event.type) {
            StoryGroupEventType.COMPLETE_NEXT -> {
                val currentItem = binding.viewPagerStories.currentItem
                if (currentItem + 1 > storyGroupModel.storyGroups.size - 1) {
                    finish()
                    return
                }
                binding.viewPagerStories.currentItem = currentItem + 1
                binding.viewPagerStories.adapter?.notifyDataSetChanged()
            }
            StoryGroupEventType.COMPLETE_PREVIOUS -> {
                val currentItem = binding.viewPagerStories.currentItem
                if (currentItem - 1 < 0) {
                    finish()
                    return
                }
                binding.viewPagerStories.currentItem = currentItem - 1
                binding.viewPagerStories.adapter?.notifyDataSetChanged()
            }
        }
    }

    @Subscribe
    fun onStoryEvent(event: StoryEvent) {
        when (event.type) {
            StoryEventType.CLOSE -> {
                finish()
            }
            else -> return
        }
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
}