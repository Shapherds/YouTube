package com.app.youtubeedu.presenter

import com.app.youtubeedu.contract.DetailContract
import com.app.youtubeedu.data.Video
import com.app.youtubeedu.error.NoInternetConnectionException
import com.app.youtubeedu.interactor.RelatedVideoLoaderInteractor
import com.app.youtubeedu.util.StringProvider
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class VideoDetailsPresenterTest {

    @MockK
    private lateinit var router: DetailContract.Router

    @MockK
    private lateinit var videoLoaderInteractor: RelatedVideoLoaderInteractor

    @MockK
    private lateinit var view: DetailContract.View

    @MockK
    private lateinit var testVideo: Video

    @RelaxedMockK
    private lateinit var srtingProvider: StringProvider

    private lateinit var detailsPresenter: VideoDetailsPresenter
    private lateinit var videoList: List<Video>

    @BeforeEach
    private fun setup() {
        MockKAnnotations.init(this)
        videoList = listOf(mockk(), mockk(), mockk(), mockk())
        detailsPresenter = VideoDetailsPresenter(videoLoaderInteractor, router, srtingProvider)
        detailsPresenter.attachView(view)
    }

    @AfterEach
    private fun clearMock() {
        clearAllMocks()
    }

    @Test
    fun testOnBackClick() {
        detailsPresenter.onBackClick()

        verifySequence {
            router.back()
        }
        confirmVerified(router)
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
        confirmVerified(view, videoLoaderInteractor)
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
        confirmVerified(view, videoLoaderInteractor)
    }

    @Test
    fun testOnItemClick() {
        detailsPresenter.onItemClick(testVideo)

        coVerifySequence {
            view.showProgress()
            view.setNewVideo(testVideo)
            view.playVideo(testVideo)
            videoLoaderInteractor(testVideo)
            view.showRelatedVideoList(videoList)
            view.hideProgress()
        }
        confirmVerified(view)
    }

    @Test
    fun testInternetConnectionRelatedVideo() {
        coEvery { videoLoaderInteractor(testVideo) } throws(NoInternetConnectionException())
        val errorText = "No internet Connection"

        detailsPresenter.loadRelatedVideoList(testVideo)

        coVerifySequence {
            view.showProgress()
            videoLoaderInteractor(testVideo)
            view.showError(errorText)
            view.hideProgress()
        }
        confirmVerified(view, videoLoaderInteractor)
    }

    @Test
    fun testVideoPlay(){
        detailsPresenter.playVideo(testVideo)

        verifySequence {
           view.playVideo(testVideo)
        }
        confirmVerified(view)
    }
}