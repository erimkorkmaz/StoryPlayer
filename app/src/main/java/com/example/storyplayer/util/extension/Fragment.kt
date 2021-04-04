package com.example.storyplayer.util.extension

import android.os.Bundle
import androidx.fragment.app.Fragment

inline fun <T : Fragment> T.withArgs(argsBuilder: Bundle.() -> Unit): T = this.apply {
    arguments = Bundle().apply(argsBuilder)
}