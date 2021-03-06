package com.example.storyplayer.ui.component

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.storyplayer.data.model.Story
import com.example.storyplayer.databinding.ViewStoryImageBinding
import com.example.storyplayer.event.StoryEvent
import com.example.storyplayer.event.StoryEventType
import com.example.storyplayer.helper.OnSwipeListener
import org.greenrobot.eventbus.EventBus

class StoryImageView : ConstraintLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    )

    private var binding: ViewStoryImageBinding =
        ViewStoryImageBinding.inflate(LayoutInflater.from(context), this, true)
    private var leftOfScreenPixels: Int = 0
    private var adapterPosition: Int = -1

    init {
        calculateTouch()
    }

    private fun calculateTouch() {
        val displayMetrics = context.resources.displayMetrics
        leftOfScreenPixels = displayMetrics.widthPixels * 40 / 100
    }

    fun bind(item: Story, position: Int) {
        binding.progressCircular.visibility = View.VISIBLE
        EventBus.getDefault().post(
            StoryEvent(
                StoryEventType.PAUSE,
                adapterPosition
            )
        )
        Glide.with(context).load(item.imageUrl).listener(object: RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                Log.e("StoryImage", "Image not loaded")
                Toast.makeText(context, "Image not loaded", Toast.LENGTH_SHORT).show()
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                binding.progressCircular.visibility = View.GONE
                EventBus.getDefault().post(
                    StoryEvent(
                        StoryEventType.RESUME,
                        adapterPosition
                    )
                )
                return false
            }

        }).into(binding.contentImage)
        adapterPosition = position
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        gestureDetector.onTouchEvent(event)
        if (event?.actionMasked == MotionEvent.ACTION_UP) {
            EventBus.getDefault()
                .post(StoryEvent(StoryEventType.RESUME, adapterPosition))
        }
        return true
    }

    private val gestureDetector = GestureDetector(context, object : OnSwipeListener() {

        override fun onSwipe(direction: Direction): Boolean {
            handleSwipe(direction)
            return true
        }

        override fun onShowPress(e: MotionEvent) {
            super.onShowPress(e)
            EventBus.getDefault().post(
                StoryEvent(
                    StoryEventType.PAUSE,
                    adapterPosition
                )
            )
        }

        override fun onSingleTapConfirmed(event: MotionEvent): Boolean {
            if (event.rawX.toInt() <= leftOfScreenPixels) {
                EventBus.getDefault().post(
                    StoryEvent(
                        StoryEventType.PREVIOUS,
                        adapterPosition
                    )
                )
            } else {
                EventBus.getDefault().post(
                    StoryEvent(
                        StoryEventType.NEXT,
                        adapterPosition
                    )
                )
            }
            return true
        }
    })

    private fun handleSwipe(direction: OnSwipeListener.Direction?) {
        if (direction != null) {
            if (direction === OnSwipeListener.Direction.DOWN) {
                EventBus.getDefault().post(
                    StoryEvent(
                        StoryEventType.CLOSE,
                        adapterPosition
                    )
                )
            }
        }
    }
}