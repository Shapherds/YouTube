package com.app.youtubeedu.util

import androidx.annotation.StringRes

interface StringProvider {

    fun provideString(@StringRes id: Int): String
}