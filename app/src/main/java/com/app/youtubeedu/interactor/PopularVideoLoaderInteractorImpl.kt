package com.app.youtubeedu.interactor

import com.app.youtubeedu.repository.VideoRepository

class PopularVideoLoaderInteractorImpl(private val repository: VideoRepository) :
    PopularVideoLoaderInteractor {

    override fun invoke() = repository.getVideoList()
}