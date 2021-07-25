package com.app.youtubeedu.repository

import com.app.youtubeedu.data.Video
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor(
    private val localDataSource: YouTubeLocalDataSource,
    private val remoteDataSource: YouTubeRemoteDataSource,
) : VideoRepository {

    override suspend fun getRelatedVideoList(video: Video): List<Video> {
        return remoteDataSource.getRelatedVideoList(video)
    }

    override fun getVideoList(): Flow<List<Video>> = flow {
        emit(localDataSource.getLocalList())
        val popularList = remoteDataSource.getPopularVideo()
        emit(popularList)
        localDataSource.saveLocalList(popularList)
    }

    override suspend fun getVideoListByName(query: String) = remoteDataSource.getVideoByName(query)
}