package com.example.storyplayer.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.storyplayer.data.model.StoryGroupModel
import com.example.storyplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var userStoryGroupAdapter: UserStoryGroupAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        subscribeDummyData()
    }

    private fun subscribeDummyData() {
        mainViewModel.dummyResponse.observe(this, Observer {
            userStoryGroupAdapter =
                UserStoryGroupAdapter { storyGroupModel: StoryGroupModel, position: Int ->
                    startActivity(
                        Intent(
                            StoryGroupActivity.newIntent(
                                this,
                                storyGroupModel,
                                position
                            )
                        )
                    )
                }
            userStoryGroupAdapter.setStoryGroup(it)
            binding.recyclerviewUserStoryGroups.adapter = userStoryGroupAdapter
        })
    }
}