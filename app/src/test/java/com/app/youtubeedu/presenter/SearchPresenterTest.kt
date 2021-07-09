package com.app.youtubeedu.presenter

import com.app.youtubeedu.R
import com.app.youtubeedu.contract.SearchContract
import com.app.youtubeedu.data.Video
import com.app.youtubeedu.error.NoInternetConnectionException
import com.app.youtubeedu.interactor.PopularVideoLoaderInteractor
import com.app.youtubeedu.interactor.VideoByNameLoaderInteractor
import com.app.youtubeedu.util.StringProvider
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SearchPresenterTest {

    @RelaxedMockK
    private lateinit var router: SearchContract.Router

    @RelaxedMockK
    private lateinit var searchInteractor: VideoByNameLoaderInteractor

    @RelaxedMockK
    private lateinit var popularVideoLoaderInteractor: PopularVideoLoaderInteractor

    @RelaxedMockK
    private lateinit var view: SearchContract.View

    @MockK
    private lateinit var srtingProvider: StringProvider

    private lateinit var searchPresenter: SearchPresenter

    @MockK
    private lateinit var testVideo: Video

    private lateinit var videoList: List<Video>

    @ExperimentalCoroutinesApi
    @BeforeEach
    private fun setup() {
        MockKAnnotations.init(this)
        videoList = listOf(mockk(), mockk(), mockk(), mockk())
        Dispatchers.setMain(TestCoroutineDispatcher())
        searchPresenter =
            SearchPresenter(router, searchInteractor, popularVideoLoaderInteractor, srtingProvider)
        searchPresenter.attachView(view)
    }

    @AfterEach
    private fun clearMock() {
        clearAllMocks()
    }

    @ExperimentalCoroutinesApi
    @AfterEach
    private fun resetDispatchers() {
        Dispatchers.resetMain()
    }

    @Test
    fun testOnItemClick() {
        searchPresenter.onItemClick(testVideo)

        verifySequence {
            router.openVideoDetails(testVideo)
        }
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
    }

    @Test
    fun testLoadVideoListWithLocalData() {
        val remoteVideoList = listOf<Video>(mockk(), mockk(), mockk(), mockk())
        every { popularVideoLoaderInteractor() } returns flow {
            emit(videoList)
            emit(remoteVideoList)
        }

        searchPresenter.loadVideoList()

        coVerifySequence {
            view.showProgress()
            popularVideoLoaderInteractor()
            view.showVideoList(videoList)
            view.showVideoList(remoteVideoList)
            view.hideProgress()
        }
    }

    @Test
    fun testLoadVideoListWithLocalDataWithoutInternetConnection() {
        val exception = NoInternetConnectionException()
        val message = "mess"
        every { popularVideoLoaderInteractor() } returns flow {
            emit(videoList)
            throw exception
        }
        every{ srtingProvider.provideString(R.string.no_internet_message)} returns message

        searchPresenter.loadVideoList()

        coVerifySequence {
            view.showProgress()
            popularVideoLoaderInteractor()
            view.showVideoList(videoList)
            view.hideProgress()
            view.showError(message)
        }
    }

    @Test
    fun testSearchListByName() {
        val testSearch = "testString"
        coEvery { searchInteractor(testSearch) } returns videoList

        searchPresenter.searchVideoByName(testSearch)

        coVerifySequence {
            view.showProgress()
            searchInteractor(testSearch)
            view.showVideoList(videoList)
            view.hideProgress()
        }
    }

    @Test
    fun testEmptyResult() {
        val searchText = "some bad  string"
        val emptyList = listOf<Video>()
        coEvery { searchInteractor(searchText) } returns emptyList

        searchPresenter.searchVideoByName(searchText)

        coVerifySequence {
            view.showProgress()
            searchInteractor(searchText)
            view.showVideoList(emptyList)
            view.hideProgress()
        }
    }

    @Test
    fun testInternetConnectionInPopularLoader() {
        val exception = NoInternetConnectionException()
        val message = "mess"
        every { popularVideoLoaderInteractor() }.throws(exception)
        every{ srtingProvider.provideString(R.string.no_internet_message)} returns message

        searchPresenter.loadVideoList()

        coVerifySequence {
            view.showProgress()
            popularVideoLoaderInteractor()
            view.hideProgress()
            view.showError(message)
        }
    }

    @Test
    fun testInternetConnectionInSearchLoader() {
        val searchText = "name"
        val message = "mess"
        val exception = NoInternetConnectionException()
        coEvery { searchInteractor(searchText) } throws(exception)
        every{ srtingProvider.provideString(R.string.no_internet_message)} returns message

        searchPresenter.searchVideoByName(searchText)

        coVerifySequence {
            view.showProgress()
            searchInteractor(searchText)
            view.hideProgress()
            view.showError(message)
        }
    }
}