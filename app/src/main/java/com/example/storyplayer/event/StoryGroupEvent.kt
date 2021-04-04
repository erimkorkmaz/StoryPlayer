package com.example.storyplayer.event

enum class StoryGroupEventType {
    COMPLETE_NEXT,
    COMPLETE_PREVIOUS
}

class StoryGroupEvent(val type: StoryGroupEventType)