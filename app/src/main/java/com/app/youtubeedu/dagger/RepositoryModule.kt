package com.app.youtubeedu.dagger

import com.app.youtubeedu.repository.VideoRepository
import com.app.youtubeedu.repository.VideoRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    fun bindVideoRepository(repositoryImpl: VideoRepositoryImpl): VideoRepository
}