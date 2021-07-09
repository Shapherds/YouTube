package com.app.youtubeedu.repository

import com.app.youtubeedu.data.Video

class YouTubeDataSourceImpl : YouTubeDataSource {

    override fun getPopularVideo(): List<Video> {
        return listOf(
            Video(
                name = "some video name",
                description = "some video description",
                videoId = "123456789",
                iconUri = "https://raw.githubusercontent.com/dimaTiurenkoJulper/datas/main/android.png",
                views = 1000,
                likes = 500,
                dislikes = 210
            ),
            Video(
                name = "some video name",
                description = "some video description",
                videoId = "234567890",
                iconUri = "https://raw.githubusercontent.com/dimaTiurenkoJulper/datas/main/android.png",
                views = 1100,
                likes = 600,
                dislikes = 220
            ),
        )
    }

    override fun getVideoByName(searchText: String): List<Video> {
        return createSimpleList().filter { it.name.contains(searchText) }
    }

    override fun getRelatedVideoList(video: Video): List<Video> {
        return listOf(
            Video(
                name = "some video name",
                description = "some video description",
                videoId = "355274241",
                iconUri = "https://raw.githubusercontent.com/dimaTiurenkoJulper/datas/main/android.png",
                views = 1000,
                likes = 500,
                dislikes = 210
            ),
            Video(
                name = "some video name",
                description = "some video description ",
                videoId = "2395723022",
                iconUri = "https://raw.githubusercontent.com/dimaTiurenkoJulper/datas/main/android.png",
                views = 1100,
                likes = 600,
                dislikes = 220
            ),
        )
    }

    private fun createSimpleList(): List<Video> {
        return listOf(
            Video(
                name = "some video name",
                description = "some video description",
                videoId = "293452614",
                iconUri = "https://raw.githubusercontent.com/dimaTiurenkoJulper/datas/main/android.png",
                views = 1000,
                likes = 500,
                dislikes = 210
            ),
            Video(
                name = "some video name",
                description = "some video description",
                videoId = "937450271",
                iconUri = "https://raw.githubusercontent.com/dimaTiurenkoJulper/datas/main/android.png",
                views = 1100,
                likes = 600,
                dislikes = 220
            ),
        )
    }
}