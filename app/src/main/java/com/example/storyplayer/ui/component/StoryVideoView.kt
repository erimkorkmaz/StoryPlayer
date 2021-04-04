package com.example.storyplayer.ui.component

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.storyplayer.R
import com.example.storyplayer.data.model.Story
import com.example.storyplayer.databinding.ViewStoryVideoBinding
import com.example.storyplayer.event.DurationChangedEvent
import com.example.storyplayer.event.StoryEvent
import com.example.storyplayer.event.StoryEventType
import com.example.storyplayer.helper.OnSwipeListener
import com.github.aakira.playermanager.DataSourceCreator
import com.github.aakira.playermanager.ExoPlayerManager
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.util.Util
import org.greenrobot.eventbus.EventBus

class StoryVideoView : ConstraintLayout {

    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    )

    private var binding: ViewStoryVideoBinding =
        ViewStoryVideoBinding.inflate(LayoutInflater.from(context), this, true)
    private var leftOfScreenPixels: Int = 0
    private var adapterPosition: Int = -1
    private lateinit var playerManager: ExoPlayerManager

    init {
        calculateTouch()
    }

    private fun calculateTouch() {
        val displayMetrics = context.resources.displayMetrics
        leftOfScreenPixels = displayMetrics.widthPixels * 40 / 100
    }

    fun bind(item: Story, position: Int) {
        adapterPosition = position
        EventBus.getDefault().post(StoryEvent(StoryEventType.PAUSE, position))
        playerManager = ExoPlayerManager.Builder(context).run {
            build(
                renderersFactory = createRenderersFactory(),
                loadControl = createDefaultLoadControl(),
                drmSessionManager = null
            )
        }
        playerManager.injectView(binding.contentVideoPlayerView)
        val dataSourceUrlBuilder = DataSourceCreator.UrlBuilder(
            url = item.videoUrl,
            userAgent = Util.getUserAgent(context, context.getString(R.string.app_name))
        )
        playerManager.setExtractorMediaSource(dataSourceUrlBuilder.build())
        playerManager.addOnStateChangedListener { playWhenReady, playbackState ->
            when (playbackState) {
                Player.STATE_IDLE -> {
                    Log.d("Player State", "idle")
                    binding.loadingProgressBar.show()

                }
                Player.STATE_BUFFERING -> {
                    Log.d("Player State", "buffering")
                    binding.loadingProgressBar.show()
                }
                Player.STATE_READY -> {
                    EventBus.getDefault()
                        .post(DurationChangedEvent(playerManager.player?.duration, position))
                    binding.loadingProgressBar.hide()
                    EventBus.getDefault().post(StoryEvent(StoryEventType.RESUME, position))
                }
            }
        }
        playerManager.play()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        gestureDetector.onTouchEvent(event)
        if (event?.actionMasked == MotionEvent.ACTION_UP) {
            playerManager.play()
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
            playerManager.pause()
            EventBus.getDefault().post(
                StoryEvent(
                    StoryEventType.PAUSE,
                    adapterPosition
                )
            )
        }

        override fun onSingleTapConfirmed(event: MotionEvent): Boolean {
            if (event.rawX.toInt() <= leftOfScreenPixels) {
                playerManager.stop()
                EventBus.getDefault().post(
                    StoryEvent(
                        StoryEventType.PREVIOUS,
                        adapterPosition
                    )
                )
            } else {
                playerManager.stop()
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
                playerManager.release()
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