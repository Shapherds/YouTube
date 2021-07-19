package com.app.youtubeedu.dagger

import com.app.youtubeedu.view.DetailsActivity
import com.app.youtubeedu.view.SearchActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [SearchActivityModule::class])
    fun contributeSearchActivityInjector(): SearchActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [DetailsActivityModule::class])
    fun contributeDetailsActivityInjector(): DetailsActivity
}
