package com.example.storyplayer.event

enum class StoryEventType {
    RESUME,
    PAUSE,
    PREVIOUS,
    NEXT,
    CLOSE,
    COMPLETE
}

class StoryEvent(val type: StoryEventType, val adapterPosition: Int)
