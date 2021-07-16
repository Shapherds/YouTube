package com.app.youtubeedu.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.youtubeedu.data.DatabaseVideo
import com.app.youtubeedu.data.Video

@Dao
interface VideoDao {

    @Query("DELETE FROM video")
    suspend fun clearYouTubeLocalVideo()

    @Query("SELECT * FROM video")
    suspend fun getLocalVideos(): List<DatabaseVideo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertYouTubeVideos(videos: List<DatabaseVideo>)
}