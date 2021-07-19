package com.app.youtubeedu.dagger

import com.app.youtubeedu.repository.YouTubeLocalDataSourceImpl
import com.app.youtubeedu.repository.YouTubeLocalDataSource
import com.app.youtubeedu.repository.YouTubeRemoteDataSource
import com.app.youtubeedu.repository.YouTubeRemoteRemoteDataSourceImpl
import dagger.Binds
import dagger.Module

@Module
interface DataSourceModule {

    @Binds
    fun bindYouTubeLocalDataSource(source: YouTubeLocalDataSourceImpl): YouTubeLocalDataSource

    @Binds
    fun bindYouTubeRemoteDataSource(sourceRemote: YouTubeRemoteRemoteDataSourceImpl): YouTubeRemoteDataSource
}