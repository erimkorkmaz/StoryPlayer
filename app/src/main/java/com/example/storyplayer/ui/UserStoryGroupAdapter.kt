package com.example.storyplayer.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.storyplayer.R
import com.example.storyplayer.data.model.StoryGroupModel
import com.example.storyplayer.databinding.ItemUserStoryBinding

class UserStoryGroupAdapter(private val itemClicked: (StoryGroupModel, Int) -> Unit) :
    RecyclerView.Adapter<UserStoryGroupViewHolder>() {
    private lateinit var storyGroupModel: StoryGroupModel

    fun setStoryGroup(storyGroupModel: StoryGroupModel) {
        this.storyGroupModel = storyGroupModel
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserStoryGroupViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return UserStoryGroupViewHolder(
            ItemUserStoryBinding.inflate(
                inflater,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = storyGroupModel.storyGroups.size

    override fun onBindViewHolder(holder: UserStoryGroupViewHolder, position: Int) {
        holder.bind(storyGroupModel, storyGroupModel.storyGroups[position], position, itemClicked)
    }

}

class UserStoryGroupViewHolder(val binding: ItemUserStoryBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        storyGroupModel: StoryGroupModel,
        storyGroup: StoryGroupModel.StoryGroup,
        position: Int,
        itemClicked: (StoryGroupModel, Int) -> Unit
    ) {
        Glide.with(binding.imageUser).load(storyGroup.profileImageUrl).apply(
            RequestOptions().circleCrop().error(R.drawable.ic_launcher_background)
        ).into(binding.imageUser)

        binding.textUsername.text = storyGroup.username

        binding.layoutUserStoryGroup.setOnClickListener {
            itemClicked.invoke(storyGroupModel, position)
        }
    }
}
