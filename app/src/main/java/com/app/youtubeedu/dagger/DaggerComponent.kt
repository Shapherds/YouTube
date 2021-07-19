package com.app.youtubeedu.dagger

import android.content.Context
import com.app.youtubeedu.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DataSourceModule::class,
        InteractorModule::class,
        RepositoryModule::class,
        StringProviderModule::class,
        AndroidSupportInjectionModule::class,
        AndroidInjectionModule::class,
        ActivityModule_ContributeSearchActivityInjector::class
    ]
)
interface DaggerComponent : AndroidInjector<DaggerApplication> {

    fun inject(instance: App)

    @Component.Builder
    interface ComponentBuilder {

        @BindsInstance
        fun context(context: Context): ComponentBuilder

        fun build(): DaggerComponent
    }
}