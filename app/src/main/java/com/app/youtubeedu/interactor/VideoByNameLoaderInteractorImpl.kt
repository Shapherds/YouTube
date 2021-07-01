package com.app.youtubeedu.interactor

import com.app.youtubeedu.repository.VideoRepository

class VideoByNameLoaderInteractorImpl(private val repository: VideoRepository) :
    VideoByNameLoaderInteractor {

    override suspend fun invoke(searchText: String) = repository.getVideoListByName(searchText)
}