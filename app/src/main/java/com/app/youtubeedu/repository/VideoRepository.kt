package com.app.youtubeedu.repository

import com.app.youtubeedu.data.Video
import kotlinx.coroutines.flow.Flow

interface VideoRepository {

    fun getRelatedVideoList(video: Video): List<Video>

    fun getVideoList(): Flow<List<Video>>

    fun getVideoListByName(searchText: String): List<Video>
}