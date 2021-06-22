package com.app.youtubeedu.presenter

import com.app.youtubeedu.contract.SearchContract
import com.app.youtubeedu.data.Video
import com.app.youtubeedu.error.NoInternetConnectionException
import com.app.youtubeedu.interactor.PopularVideoLoaderInteractor
import com.app.youtubeedu.interactor.VideoByNameLoaderInteractor
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SearchPresenterTest{

    @MockK
    private lateinit var router: SearchContract.Router

    @MockK
    private lateinit var searchInteractor: VideoByNameLoaderInteractor

    @MockK
    private lateinit var popularVideoLoaderInteractor: PopularVideoLoaderInteractor

    @MockK
    private lateinit var view: SearchContract.View

    private lateinit var searchPresenter: SearchPresenter

    @MockK
    private lateinit var testVideo: Video

    private lateinit var videoList: List<Video>

    @BeforeEach
    private fun setup() {
        MockKAnnotations.init(this)
        videoList = listOf(mockk(), mockk(), mockk(), mockk())
        searchPresenter = SearchPresenter(router, searchInteractor, popularVideoLoaderInteractor)
        searchPresenter.attachView(view)
    }

    @AfterEach
    private fun clearMock() {
        clearAllMocks()
    }

    @Test
    fun testOnItemClick() {
        searchPresenter.onItemClick(testVideo)

        verifySequence {
            router.openVideoDetails(testVideo)
        }
        confirmVerified(router)
    }

    @Test
    fun testLoadVideoList() {
        every { popularVideoLoaderInteractor() } returns flowOf(videoList)

        searchPresenter.loadVideoList()

        verifySequence {
            view.showProgress()
            popularVideoLoaderInteractor()
            view.showVideoList(videoList)
            view.hideProgress()
        }
        confirmVerified(view, popularVideoLoaderInteractor)
    }

    @Test
    fun testLoadVideoListWithLocalData() {
        val remoteVideoList = listOf<Video>(mockk(), mockk(), mockk(), mockk())
        every { popularVideoLoaderInteractor() } returns flow {
            emit(videoList)
            emit(remoteVideoList)
        }

        searchPresenter.loadVideoList()

        verifySequence {
            popularVideoLoaderInteractor()
            view.showProgress()
            view.showVideoList(videoList)
            view.showVideoList(remoteVideoList)
            view.hideProgress()
        }
        confirmVerified(view, popularVideoLoaderInteractor)
    }

    @Test
    fun testLoadVideoListWithLocalDataWithoutInternetConnection() {
        every { popularVideoLoaderInteractor() } returns flow {
            emit(videoList)
            throw NoInternetConnectionException()
        }
        val errorText = "No internet Connection"

        searchPresenter.loadVideoList()

        verifySequence {
            popularVideoLoaderInteractor()
            view.showProgress()
            view.showVideoList(videoList)
            view.showError(errorText)
            view.hideProgress()
        }
        confirmVerified(view, popularVideoLoaderInteractor)
    }

    @Test
    fun testSearchListByName() {
        val testSearch = "testString"
        coEvery { searchInteractor(testSearch) } returns videoList

        searchPresenter.searchListByName(testSearch)

        verifySequence {
            view.showProgress()
            view.showVideoList(videoList)
            view.hideProgress()
        }
        confirmVerified(view, searchInteractor)
    }

    @Test
    fun testEmptyResult() {
        val searchText = "some bad  string"
        val emptyList = listOf<Video>()
        coEvery { searchInteractor(searchText) } returns emptyList

        searchPresenter.searchListByName(searchText)

        verifySequence {
            view.showProgress()
            view.showVideoList(videoList)
            view.hideProgress()
        }
        confirmVerified(view, searchInteractor)
    }

    @Test
    fun testInternetConnectionInPopularLoader() {
        every { popularVideoLoaderInteractor() }.throws(NoInternetConnectionException())
        val errorText = "No internet Connection"

        searchPresenter.loadVideoList()

        verifySequence {
            view.showProgress()
            popularVideoLoaderInteractor()
            view.showError(errorText)
            view.hideProgress()
        }
        confirmVerified(view, popularVideoLoaderInteractor)
    }

    @Test
    fun testInternetConnectionInSearchLoader() {
        val searchText = "name"
        val errorText = "No internet Connection"
        coEvery { searchInteractor(searchText) }.throws(NoInternetConnectionException())

        searchPresenter.searchListByName(searchText)

        coVerifySequence {
            view.showProgress()
            searchInteractor(searchText)
            view.showError(errorText)
            view.hideProgress()
        }
        confirmVerified(view, searchInteractor)
    }
}

