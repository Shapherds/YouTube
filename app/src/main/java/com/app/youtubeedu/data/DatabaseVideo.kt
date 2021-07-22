package com.app.youtubeedu.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "video")
data class DatabaseVideo(
    @ColumnInfo(name = "video_name") val name: String,
    @ColumnInfo(name = "video_description") val description: String,
    @ColumnInfo(name = "video_id") @PrimaryKey val videoId: String,
    @ColumnInfo(name = "video_icon_uri") val iconUri: String,
    @ColumnInfo(name = "video_views") val views: Long,
    @ColumnInfo(name = "video_likes") val likes: Long,
    @ColumnInfo(name = "video_dislikes") val dislikes: Long,
)