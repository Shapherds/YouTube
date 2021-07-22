package com.app.youtubeedu.dagger

import android.content.Context
import androidx.room.Room
import com.app.youtubeedu.database.LocalDatabase
import com.app.youtubeedu.database.VideoDao
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule {

    @Provides
    fun provideDatabase(context: Context): LocalDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            LocalDatabase::class.java,
            "database")
            .build()
    }

    @Provides
    fun provideVideoDao(localDatabase: LocalDatabase): VideoDao {
        return localDatabase.getVideoDao()
    }
}