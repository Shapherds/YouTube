package com.app.youtubeedu.presenter

import android.util.Log
import com.app.youtubeedu.contract.BaseContract
import kotlinx.coroutines.*

abstract class BasePresenter<T : BaseContract.View> : BaseContract.Presenter<T> , CoroutineScope {

    protected var view: T? = null

    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        Log.e("Logs", " catch error :  $exception")
        view?.showError(exception.toString())
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