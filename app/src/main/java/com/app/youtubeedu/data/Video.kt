package com.app.youtubeedu.data

data class Video(
    val name: String,
    val description: String,
    val videoId: String,
    val iconUri: String,
    val views: Long,
    val likes: Long,
    val dislikes: Long,
)