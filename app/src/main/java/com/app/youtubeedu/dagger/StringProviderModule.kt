package com.app.youtubeedu.dagger

import com.app.youtubeedu.util.StringProvider
import com.app.youtubeedu.util.StringProviderImpl
import dagger.Binds
import dagger.Module

@Module
interface StringProviderModule {
    
    @Binds
    fun bindStringProvider(provider: StringProviderImpl): StringProvider
}