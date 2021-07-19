package com.app.youtubeedu.router

import com.app.youtubeedu.contract.DetailContract
import com.app.youtubeedu.dagger.ActivityScope
import com.app.youtubeedu.view.DetailsActivity
import javax.inject.Inject

@ActivityScope
class DetailsRouter @Inject constructor(private val activity: DetailsActivity) :
    BaseRouter(activity), DetailContract.Router {

    override fun back() {
        activity.finish()
    }
}