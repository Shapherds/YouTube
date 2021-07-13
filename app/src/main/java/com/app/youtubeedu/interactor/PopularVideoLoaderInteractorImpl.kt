package com.app.youtubeedu.interactor

import com.app.youtubeedu.repository.VideoRepository
import javax.inject.Inject

class PopularVideoLoaderInteractorImpl @Inject constructor(private val repository: VideoRepository) :
    PopularVideoLoaderInteractor {

    override fun invoke() = repository.getVideoList()
}