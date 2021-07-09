package com.app.youtubeedu.view

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.youtubeedu.R
import com.app.youtubeedu.contract.BaseContract
import com.app.youtubeedu.presenter.BasePresenter

abstract class BaseActivity<T : BasePresenter<out BaseContract.View>> : AppCompatActivity(),
    BaseContract.View {

    @Suppress("LeakingThis")
    protected var presenter = createPresenter()

    @Suppress("DEPRECATION")
    private lateinit var progressDialog: ProgressDialog

    protected abstract fun createPresenter(): T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onAttachView()
    }

    override fun onDestroy() {
        onDetachView()
        super.onDestroy()
    }

    @Suppress("UNCHECKED_CAST")
    private fun onAttachView() {
        val view = this as BaseActivity<BasePresenter<BaseContract.View>>
        presenter.attachView(view)
    }

    private fun onDetachView() {
        presenter.detachView()
    }

    @Suppress("DEPRECATION")
    override fun showProgress() {
        progressDialog = ProgressDialog(this).apply {
            setMessage(getString(R.string.progress_message))
            show()
        }
    }

    override fun hideProgress() {
        progressDialog.hide()
    }

    override fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }
}