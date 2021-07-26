package com.app.youtubeedu.util

import android.content.Context
import androidx.annotation.StringRes
import javax.inject.Inject

class StringProviderImpl @Inject constructor(private val context: Context) : StringProvider {

    override fun provideString(@StringRes id: Int) = context.getString(id)

    override fun provideString(id: Int, vararg formatting: Any) = context.getString(id, *formatting)
}