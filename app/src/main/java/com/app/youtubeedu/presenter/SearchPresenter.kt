package com.app.youtubeedu.presenter

import com.app.youtubeedu.contract.SearchContract
import com.app.youtubeedu.data.Video
import com.app.youtubeedu.interactor.PopularVideoLoaderInteractor
import com.app.youtubeedu.interactor.VideoByNameLoaderInteractor
import com.app.youtubeedu.util.StringProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchPresenter @Inject constructor(
    private val router: SearchContract.Router,
    private val videoByNameLoaderInteractor: VideoByNameLoaderInteractor,
    private val popularVideoLoaderInteractor: PopularVideoLoaderInteractor,
    stringProvider: StringProvider,
) : BasePresenter<SearchContract.View>(stringProvider), SearchContract.Presenter {

    override fun onItemClick(video: Video) {
        router.openVideoDetails(video)
    }

    override fun loadVideoList() {
        launch {
            try {
                view?.showProgress()
                popularVideoLoaderInteractor().collect { value ->
                    view?.showVideoList(value)
                }
            } finally {
                view?.hideProgress()
            }
        }
    }

    override fun searchVideoByName(searchText: String) {
        launch {
            try {
                view?.showProgress()
                val videoList = videoByNameLoaderInteractor(searchText)
                view?.showVideoList(videoList)
            } finally {
                view?.hideProgress()
            }
        }
    }
}