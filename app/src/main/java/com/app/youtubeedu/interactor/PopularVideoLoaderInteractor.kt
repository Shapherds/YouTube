package com.app.youtubeedu.interactor

import com.app.youtubeedu.data.Video
import kotlinx.coroutines.flow.Flow

interface PopularVideoLoaderInteractor {

    operator fun invoke(): Flow<List<Video>>
}