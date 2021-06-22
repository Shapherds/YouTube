package com.app.youtubeedu.presenter

import com.app.youtubeedu.contract.SearchContract
import com.app.youtubeedu.data.Video
import com.app.youtubeedu.interactor.PopularVideoLoaderInteractor
import com.app.youtubeedu.interactor.VideoByNameLoaderInteractor

class SearchPresenter(
    private val router: SearchContract.Router,
    private val videoByNameLoaderInteractor: VideoByNameLoaderInteractor,
    private val popularVideoLoaderInteractor: PopularVideoLoaderInteractor
) : BasePresenter<SearchContract.View>() {

    fun onItemClick(video: Video){
        TODO("not yet implemented")
    }

    fun loadVideoList(){
        TODO("not yet implemented")
    }

    fun searchListByName(searchText: String){
        TODO("not yet implemented")
    }
}