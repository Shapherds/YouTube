package com.app.youtubeedu.interactor

import com.app.youtubeedu.repository.VideoRepository
import javax.inject.Inject

class VideoByNameLoaderInteractorImpl @Inject constructor(private val repository: VideoRepository) :
    VideoByNameLoaderInteractor {

    override suspend fun invoke(query: String) = repository.getVideoListByName(query)
}