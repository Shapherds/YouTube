package com.app.youtubeedu.dagger

import com.app.youtubeedu.contract.DetailContract
import com.app.youtubeedu.presenter.DetailsPresenter
import com.app.youtubeedu.router.DetailsRouter
import dagger.Binds
import dagger.Module

@Module
interface DetailsActivityModule {

    @ActivityScope
    @Binds
    fun bindDetailsPresenter(presenter: DetailsPresenter): DetailContract.Presenter

    @ActivityScope
    @Binds
    fun bindDetailRouter(router: DetailsRouter): DetailContract.Router
}