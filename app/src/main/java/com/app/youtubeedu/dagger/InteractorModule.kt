package com.app.youtubeedu.dagger

import com.app.youtubeedu.interactor.*
import dagger.Binds
import dagger.Module

@Module
interface InteractorModule {

    @Binds
    fun bindPopularVideoLoaderInteractor(interactor: PopularVideoLoaderInteractorImpl): PopularVideoLoaderInteractor

    @Binds
    fun bindRelatedVideoLoaderInteractor(interactor: RelatedVideoLoaderInteractorImpl): RelatedVideoLoaderInteractor

    @Binds
    fun bindVideoByNameLoaderInteractor(interactor: VideoByNameLoaderInteractorImpl): VideoByNameLoaderInteractor
}
