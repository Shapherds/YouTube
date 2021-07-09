package com.app.youtubeedu.presenter

import android.util.Log
import com.app.youtubeedu.R
import com.app.youtubeedu.contract.BaseContract
import com.app.youtubeedu.error.NoInternetConnectionException
import com.app.youtubeedu.util.StringProvider
import kotlinx.coroutines.*

abstract class BasePresenter<T : BaseContract.View>(private val stringProvider: StringProvider) :
    BaseContract.Presenter<T>, CoroutineScope {

    protected var view: T? = null

    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        Log.e("Logs", " catch error :  $exception")
        when (exception) {
            is NoInternetConnectionException -> view?.showError(
                stringProvider.provideString(
                    R.string.no_internet_message
                )
            )
            else -> view?.showError(
                stringProvider.provideString(
                    R.string.something_wrong
                )
            )
        }
    }

    final override val coroutineContext = SupervisorJob() + Dispatchers.Main + errorHandler

    override fun detachView() {
        this.cancel()
        view = null
    }

    override fun attachView(view: T) {
        this.view = view
    }
}