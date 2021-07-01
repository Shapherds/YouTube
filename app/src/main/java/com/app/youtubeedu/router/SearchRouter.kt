package com.app.youtubeedu.router

import android.content.Context
import com.app.youtubeedu.contract.SearchContract
import com.app.youtubeedu.data.Video

class SearchRouter(context: Context): BaseRouter(context) , SearchContract.Router {

    override fun openVideoDetails(video: Video) {
        TODO("Not yet implemented")
    }
}