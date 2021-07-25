package com.app.youtubeedu.repository

import com.app.youtubeedu.data.Video

interface YouTubeRemoteDataSource {

    suspend fun getPopularVideo(): List<Video>

    suspend fun getVideoByName(query: String): List<Video>

    suspend fun getRelatedVideoList(video: Video): List<Video>
}