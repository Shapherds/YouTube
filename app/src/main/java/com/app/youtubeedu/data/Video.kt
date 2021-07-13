package com.app.youtubeedu.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Video(
    val name: String,
    val description: String,
    val videoId: String,
    val iconUri: String,
    val views: Long,
    val likes: Long,
    val dislikes: Long,
): Parcelable