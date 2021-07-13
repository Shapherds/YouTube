package com.app.youtubeedu.repository

import com.app.youtubeedu.data.Video
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor(
    private val localDataSource: YouTubeLocalDataSource,
    private val remoteRemoteDataSource: YouTubeRemoteDataSource
) : VideoRepository {

    override suspend fun getRelatedVideoList(video: Video): List<Video> {
        delay(1000)
        return remoteRemoteDataSource.getRelatedVideoList(video)
    }

    override fun getVideoList(): Flow<List<Video>> {
        return flow {
            delay(1000)
            emit(localDataSource.getLocalList())
            delay(2000)
            emit(remoteRemoteDataSource.getPopularVideo())
        }
    }

    override suspend fun getVideoListByName(searchText: String): List<Video> {
        delay(1000)
        return remoteRemoteDataSource.getVideoByName(searchText)
    }

}