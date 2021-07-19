package com.app.youtubeedu.dagger

import com.app.youtubeedu.contract.SearchContract
import com.app.youtubeedu.presenter.SearchPresenter
import com.app.youtubeedu.router.SearchRouter
import dagger.Binds
import dagger.Module

@Module
interface SearchActivityModule {

    @ActivityScope
    @Binds
    fun bindSearchRouter(router: SearchRouter): SearchContract.Router

    @ActivityScope
    @Binds
    fun bindSearchPresenter(presenter: SearchPresenter): SearchContract.Presenter
}