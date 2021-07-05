package com.app.youtubeedu.router

import android.app.Activity
import com.app.youtubeedu.contract.DetailContract

class DetailsRouter(private val activity: Activity) : BaseRouter(activity), DetailContract.Router {

    override fun back() {
        activity.finish()
    }
}