package com.app.youtubeedu.interactor

import com.app.youtubeedu.data.Video

interface RelatedVideoLoaderInteractor {

    suspend operator fun invoke(video: Video): List<Video>
}