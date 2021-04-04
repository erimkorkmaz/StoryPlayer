package com.example.storyplayer.ui.custom

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager

class ScrollDisabledLinearLayoutManager(context: Context) :
    LinearLayoutManager(context, HORIZONTAL, false) {

    override fun canScrollHorizontally(): Boolean {
        return false
    }

    override fun canScrollVertically(): Boolean {
        return false
    }
}