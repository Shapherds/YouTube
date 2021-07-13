package com.app.youtubeedu.interactor

import com.app.youtubeedu.data.Video
import com.app.youtubeedu.repository.VideoRepository

class RelatedVideoLoaderInteractorImpl(private val repository: VideoRepository) :
    RelatedVideoLoaderInteractor {

    override suspend fun invoke(video: Video) = repository.getRelatedVideoList(video)
}