package com.app.youtubeedu.contract

interface BaseContract {

    interface Presenter<T : View> {

        fun attachView(view: T)

        fun detachView()
    }

    interface View {

        fun showProgress()

        fun hideProgress()

        fun showError(error: String)
    }

    interface Router

}