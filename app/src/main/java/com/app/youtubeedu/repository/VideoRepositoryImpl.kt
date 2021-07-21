package com.app.youtubeedu.repository

import com.app.youtubeedu.data.Video
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor(
    private val localDataSourse: YouTubeLocalDataSource,
    private val remoteDataSource: YouTubeRemoteDataSource
) : VideoRepository {

    override suspend fun getRelatedVideoList(video: Video): List<Video> {
        delay(1000)
        return remoteDataSource.getRelatedVideoList(video)
    }

    override fun getVideoList(): Flow<List<Video>> {
        return flow {
            emit(localDataSourse.getLocalList())
            emit(remoteDataSource.getPopularVideo())
        }
    }

    override suspend fun getVideoListByName(searchText: String): List<Video> {
        delay(1000)
        return remoteDataSource.getVideoByName(searchText)
    }
}