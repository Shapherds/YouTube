package com.app.youtubeedu.presenter

import com.app.youtubeedu.R
import com.app.youtubeedu.contract.DetailContract
import com.app.youtubeedu.data.Video
import com.app.youtubeedu.error.NoInternetConnectionException
import com.app.youtubeedu.interactor.RelatedVideoLoaderInteractor
import com.app.youtubeedu.util.StringProvider
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class VideoDetailsPresenterTest {

    @RelaxedMockK
    private lateinit var router: DetailContract.Router

    @RelaxedMockK
    private lateinit var videoLoaderInteractor: RelatedVideoLoaderInteractor

    @RelaxedMockK
    private lateinit var view: DetailContract.View

    @MockK
    private lateinit var testVideo: Video

    @RelaxedMockK
    private lateinit var srtingProvider: StringProvider

    private lateinit var detailsPresenter: DetailsPresenter
    private lateinit var videoList: List<Video>

    @ExperimentalCoroutinesApi
    @BeforeEach
    private fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(TestCoroutineDispatcher())
        videoList = listOf(mockk(), mockk(), mockk(), mockk())
        detailsPresenter = DetailsPresenter(router, videoLoaderInteractor, srtingProvider)
        detailsPresenter.attachView(view)
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
    fun testOnBackClick() {
        detailsPresenter.onBackClick()

        verifySequence {
            router.back()
        }
    }

    @Test
    fun testLoadRelatedVideoList() {
        coEvery { videoLoaderInteractor(testVideo) } returns videoList

        detailsPresenter.loadRelatedVideoList(testVideo)

        coVerifySequence {
            view.showProgress()
            videoLoaderInteractor(testVideo)
            view.showRelatedVideoList(videoList)
            view.hideProgress()
        }
    }

    @Test
    fun testEmptyResult() {
        val emptyList = listOf<Video>()
        coEvery { videoLoaderInteractor(testVideo) } returns emptyList

        detailsPresenter.loadRelatedVideoList(testVideo)

        coVerifySequence {
            view.showProgress()
            videoLoaderInteractor(testVideo)
            view.showRelatedVideoList(emptyList)
            view.hideProgress()
        }
    }

    @Test
    fun testOnItemClick() {
        coEvery { videoLoaderInteractor(testVideo) } returns videoList
        
        detailsPresenter.onItemClick(testVideo)

        coVerifySequence {
            view.showProgress()
            view.showVideoData(testVideo)
            view.playVideo(testVideo)
            videoLoaderInteractor(testVideo)
            view.showRelatedVideoList(videoList)
            view.hideProgress()
        }
    }

    @Test
    fun testInternetConnectionRelatedVideo() {
        val message = "mess"
        coEvery { videoLoaderInteractor(testVideo) } throws NoInternetConnectionException()
        every{ srtingProvider.provideString(R.string.no_internet_message)} returns message

        detailsPresenter.loadRelatedVideoList(testVideo)

        coVerifySequence {
            view.showProgress()
            videoLoaderInteractor(testVideo)
            view.hideProgress()
            view.showError(message)
        }
    }

    @Test
    fun testVideoPlay() {
        detailsPresenter.playVideo(testVideo)

        verifySequence {
            view.playVideo(testVideo)
            view.showVideoData(testVideo)
        }
    }
}