package com.app.youtubeedu.repository

import com.app.youtubeedu.data.Video

interface DatabaseDataSourse {

    suspend fun getLocalList(): List<Video>
}