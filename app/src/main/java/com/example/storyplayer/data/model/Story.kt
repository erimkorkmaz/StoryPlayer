package com.example.storyplayer.data.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


enum class StoryType(val type: String) {
    VIDEO("VIDEO"),
    IMAGE("IMAGE")
}

@Parcelize
data class Story(
    @SerializedName("id") @Expose val id: String,
    @SerializedName("type") @Expose val type: String,
    @SerializedName("videoUrl") @Expose val videoUrl: String,
    @SerializedName("imageUrl") @Expose val imageUrl: String,
) : Parcelable