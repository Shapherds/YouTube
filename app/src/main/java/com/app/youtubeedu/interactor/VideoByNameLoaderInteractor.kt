package com.app.youtubeedu.interactor

import com.app.youtubeedu.data.Video

interface VideoByNameLoaderInteractor {

    operator fun invoke(searchText: String): List<Video>
}