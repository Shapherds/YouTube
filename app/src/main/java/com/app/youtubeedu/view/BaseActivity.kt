package com.app.youtubeedu.view

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.youtubeedu.R
import com.app.youtubeedu.contract.BaseContract
import com.app.youtubeedu.presenter.BasePresenter
import dagger.android.AndroidInjection
import javax.inject.Inject

abstract class BaseActivity<T : BasePresenter<out BaseContract.View>> : AppCompatActivity(),
    BaseContract.View {

    @Inject
    lateinit var presenter: T

    @Suppress("DEPRECATION")
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
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
        if (!::progressDialog.isInitialized) {
            progressDialog = ProgressDialog(this)
        }
        if (!progressDialog.isShowing) progressDialog.apply {
            setMessage(getString(R.string.progress_message))
            show()
        }
    }

    override fun hideProgress() {
        progressDialog.dismiss()
    }

    override fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }
}