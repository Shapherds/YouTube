package com.app.youtubeedu.repository

import com.app.youtubeedu.data.Video
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class VideoRepositoryImpl : VideoRepository {

    private val localDataSourse: DatabaseDataSourse = DatabaseDataSourceImpl()
    private val remoteDataSource: YouTubeDataSource = YouTubeDataSourceImpl()

    override suspend fun getRelatedVideoList(video: Video): List<Video> {
        delay(1000)
        return remoteDataSource.getRelatedVideoList(video)
    }

    override fun getVideoList(): Flow<List<Video>> {
        return flow {
            delay(1000)
            emit(localDataSourse.getLocalList())
            delay(2000)
            emit(remoteDataSource.getPopularVideo())
        }
    }

    override suspend fun getVideoListByName(searchText: String): List<Video> {
        delay(1000)
        return remoteDataSource.getVideoByName(searchText)
    }

}