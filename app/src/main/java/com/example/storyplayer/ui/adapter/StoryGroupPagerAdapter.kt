package com.example.storyplayer.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.storyplayer.data.model.StoryGroupModel
import com.example.storyplayer.ui.StoryGroupFragment

class StoryGroupPagerAdapter(
    supportFragmentManager: FragmentManager,
    private val storyGroups: ArrayList<StoryGroupModel.StoryGroup>
) : FragmentPagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        return StoryGroupFragment.newInstance(storyGroups[position], position)
    }

    override fun getCount(): Int {
        return storyGroups.size
    }
}
