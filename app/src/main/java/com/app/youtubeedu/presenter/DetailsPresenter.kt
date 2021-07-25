package com.app.youtubeedu.presenter

import com.app.youtubeedu.contract.DetailContract
import com.app.youtubeedu.data.Video
import com.app.youtubeedu.interactor.RelatedVideoLoaderInteractor
import com.app.youtubeedu.util.StringProvider
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsPresenter @Inject constructor(
    private val router: DetailContract.Router,
    private val relatedVideoLoaderInteractor: RelatedVideoLoaderInteractor,
    stringProvider: StringProvider,
) : BasePresenter<DetailContract.View>(stringProvider), DetailContract.Presenter {

    override fun onBackClick() {
        router.back()
    }

    override fun loadRelatedVideoList(video: Video) {
        launch {
            try {
                view?.showProgress()
                val videoList = relatedVideoLoaderInteractor(video)
                view?.showRelatedVideoList(videoList)
            } finally {
                view?.hideProgress()
            }
        }
    }

    override fun onItemClick(video: Video) {
        launch {
            try {
                view?.showProgress()
                view?.showVideoData(video)
                view?.playVideo(video)
                val relatedVideoList = relatedVideoLoaderInteractor(video)
                view?.showRelatedVideoList(relatedVideoList)
            } finally {
                view?.hideProgress()
            }
        }
    }

    override fun playVideo(video: Video) {
        view?.playVideo(video)
        view?.showVideoData(video)
    }

}