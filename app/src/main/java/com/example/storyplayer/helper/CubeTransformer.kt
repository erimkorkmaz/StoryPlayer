package com.example.storyplayer.helper

import android.view.View
import androidx.viewpager.widget.ViewPager


class CubeTransformer : ViewPager.PageTransformer {

    override fun transformPage(page: View, position: Float) {
        onPreTransform(page, position)
        onTransform(page, position)
    }

    private fun onPreTransform(page: View, position: Float) {
        page.rotationX = 0f
        page.rotationY = 0f
        page.rotation = 0f
        page.scaleX = 1f
        page.scaleY = 1f
        page.pivotX = 0f
        page.pivotY = 0f
        page.translationX = 0f
        page.translationY = 0f
        //Hides Off screen page need to change it later
        page.alpha = if (position <= -1f || position >= 1f) 0f else 1f
        page.isEnabled = false

    }

    private fun onTransform(page: View, position: Float) {
        page.pivotX = if (position < 0f) page.width.toFloat() else 0f
        page.pivotY = page.height * 0.5f
        page.rotationY = 27.5f * position
    }
}