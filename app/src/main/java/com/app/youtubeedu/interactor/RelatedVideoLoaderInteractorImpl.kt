package com.app.youtubeedu.interactor

import com.app.youtubeedu.data.Video
import com.app.youtubeedu.repository.VideoRepository
import javax.inject.Inject

class RelatedVideoLoaderInteractorImpl @Inject constructor(private val repository: VideoRepository) :
    RelatedVideoLoaderInteractor {

    override suspend fun invoke(video: Video) = repository.getRelatedVideoList(video)
}