package com.app.youtubeedu.repository

import com.app.youtubeedu.data.Video
import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.model.SearchResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.google.api.services.youtube.model.Video as YouTubeVideo

class YouTubeRemoteDataSourceImpl @Inject constructor(private val youTube: YouTube) :
    YouTubeRemoteDataSource {

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun getPopularVideo() =
        withContext(Dispatchers.IO) {
            youTube
                .videos()
                .list("snippet,contentDetails,statistics")
                .setChart("mostPopular")
                .setMaxResults(20)
                .setKey(API_KEY)
                .execute()
                .items.map { item ->
                    item.getVideo()
                }
        }

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun getVideoByName(searchText: String) =
        withContext(Dispatchers.IO) {
            youTube
                .search()
                .list("snippet")
                .setKey(API_KEY)
                .setMaxResults(20)
                .setQ(searchText)
                .setType("video")
                .execute()
                .items.mapNotNull { video ->
                    getVideoInfo(video)?.getVideo()
                }
        }

    @Suppress("BlockingMethodInNonBlockingContext")
    override suspend fun getRelatedVideoList(video: Video) =
        withContext(Dispatchers.IO) {
            youTube
                .search()
                .list("snippet")
                .setKey(API_KEY)
                .setMaxResults(20)
                .setRelatedToVideoId(video.videoId)
                .setType("video")
                .execute()
                .items.mapNotNull { video ->
                    getVideoInfo(video)?.getVideo()
                }
        }

    private fun getVideoInfo(item: SearchResult) = youTube
        .videos()
        .list("snippet,contentDetails,statistics")
        .setKey(API_KEY)
        .setId(item.id.videoId)
        .execute()
        .items
        .firstOrNull()

    private fun YouTubeVideo.getVideo(): Video {
        return Video(
            name = this.snippet.title,
            description = this.snippet.description,
            videoId = this.id,
            iconUri = this.snippet.thumbnails.medium.url,
            views = this.statistics.viewCount?.toLong() ?: 0,
            likes = this.statistics.likeCount?.toLong() ?: 0,
            dislikes = this.statistics.dislikeCount?.toLong() ?: 0,
        )
    }

    companion object {

        private const val API_KEY = "AIzaSyByTm6Rjp-rGZ4wG9nu6o98y3ZSFbN1S8A"
    }
}
