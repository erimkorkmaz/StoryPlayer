package com.example.storyplayer.util.extension


inline fun <R> R?.notNull(f: (it: R) -> Unit) {
    if (this != null) {
        f(this)
    }
}