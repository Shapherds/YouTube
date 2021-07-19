package com.app.youtubeedu.repository

import com.app.youtubeedu.data.Video

interface YouTubeRemoteDataSource {

    fun getPopularVideo(): List<Video>

    fun getVideoByName(searchText: String): List<Video>

    fun getRelatedVideoList(video: Video): List<Video>
}