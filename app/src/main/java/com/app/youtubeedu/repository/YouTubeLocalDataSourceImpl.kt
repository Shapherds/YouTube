package com.app.youtubeedu.repository

import com.app.youtubeedu.data.Video
import javax.inject.Inject

class YouTubeLocalDataSourceImpl @Inject constructor() : YouTubeLocalDataSource {

    override suspend fun getLocalList(): List<Video> {
        return listOf(
            Video(
                name = "some video name",
                description = "some video description",
                videoId = "234152842",
                iconUri = "https://raw.githubusercontent.com/dimaTiurenkoJulper/datas/main/android.png",
                views = 1000,
                likes = 500,
                dislikes = 210
            ),
            Video(
                name = "some video name",
                description = "some video description",
                videoId = "941232931",
                iconUri = "https://raw.githubusercontent.com/dimaTiurenkoJulper/datas/main/android.png",
                views = 1100,
                likes = 600,
                dislikes = 220
            ),
        )
    }
}