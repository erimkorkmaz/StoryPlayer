package com.example.storyplayer.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StoryGroupModel(
    @SerializedName("userStories")
    val storyGroups: ArrayList<StoryGroup>
) : Parcelable {
    @Parcelize
    data class StoryGroup(
        @SerializedName("profileImageUrl")
        val profileImageUrl: String?,
        @SerializedName("stories")
        val stories: ArrayList<Story>,
        @SerializedName("username")
        val username: String?
    ) : Parcelable
}