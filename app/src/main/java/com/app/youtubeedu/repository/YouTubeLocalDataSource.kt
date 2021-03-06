package com.app.youtubeedu.repository

import com.app.youtubeedu.data.Video

interface YouTubeLocalDataSource {

    suspend fun getLocalList(): List<Video>

    suspend fun saveLocalList(list: List<Video>)
}