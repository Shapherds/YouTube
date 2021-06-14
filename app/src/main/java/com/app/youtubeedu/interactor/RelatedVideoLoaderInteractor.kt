package com.app.youtubeedu.interactor

import com.app.youtubeedu.data.Video

interface RelatedVideoLoaderInteractor {

    operator fun invoke(video: Video): List<Video>
}