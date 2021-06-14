package com.app.youtubeedu.contract

import com.app.youtubeedu.data.Video

interface DetailContract {

    interface Presenter : BaseContract.Presenter<View> {

        fun onBackClick()

        fun loadRelatedVideoList(video: Video)

        fun onItemClick(video: Video)
    }

    interface View : BaseContract.View {

        fun showRelatedVideoList(videoList: List<Video>)

        fun playVideo(video: Video)
    }

    interface Router {

        fun back()
    }
}