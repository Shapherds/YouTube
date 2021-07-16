package com.app.youtubeedu.repository

import com.app.youtubeedu.data.DatabaseVideo
import com.app.youtubeedu.data.Video
import com.app.youtubeedu.database.VideoDao
import javax.inject.Inject

class YouTubeLocalDataSourceImpl @Inject constructor(var videoDao: VideoDao) :
    YouTubeLocalDataSource {

    override suspend fun getLocalList(): List<Video> =
        videoDao.getLocalVideos().map { convertToVideo(it) }

    override suspend fun saveLocalList(list: List<Video>) {
        videoDao.run {
            clearYouTubeLocalVideo()
            insertYouTubeVideos(list.map { convertToDatabaseVideo(it) })
        }
    }

    private fun convertToVideo(video: DatabaseVideo) = Video(
        name = video.name,
        description = video.description,
        videoId = video.videoId,
        iconUri = video.iconUri,
        views = video.views,
        likes = video.likes,
        dislikes = video.dislikes,
    )

    private fun convertToDatabaseVideo(video: Video) = DatabaseVideo(
        name = video.name,
        description = video.description,
        videoId = video.videoId,
        iconUri = video.iconUri,
        views = video.views,
        likes = video.likes,
        dislikes = video.dislikes,
    )
}