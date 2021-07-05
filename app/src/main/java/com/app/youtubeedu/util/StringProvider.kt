package com.app.youtubeedu.util

import androidx.annotation.StringRes

interface StringProvider {

    fun provideString(@StringRes id: Int): String

    fun provideString(@StringRes id: Int, vararg formatting: Any): String
}