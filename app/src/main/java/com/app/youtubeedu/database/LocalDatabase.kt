package com.app.youtubeedu.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.youtubeedu.data.DatabaseVideo
import com.app.youtubeedu.data.Video

@Database(entities = [DatabaseVideo::class], version = 1)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun getVideoDao(): VideoDao
}