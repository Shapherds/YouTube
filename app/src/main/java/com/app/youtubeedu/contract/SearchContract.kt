package com.app.youtubeedu.contract

import com.app.youtubeedu.data.Video

interface SearchContract {

    interface View : BaseContract.View {

        fun showVideoList(videoList: List<Video>)
    }

    interface Presenter : BaseContract.Presenter<BaseContract.View> {

        fun onItemClick(video: Video)

        fun loadVideoList()

        fun searchVideoByName(searchText: String)
    }

    interface Router {

        fun openVideoDetails(video: Video)
    }
}