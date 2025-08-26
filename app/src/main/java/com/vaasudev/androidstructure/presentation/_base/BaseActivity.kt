package com.vaasudev.androidstructure.presentation._base

import android.R
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.vaasudev.androidstructure.data.datastore.DataStore
import com.vaasudev.androidstructure.domain.error_handle.NetworkError
import com.vaasudev.androidstructure.presentation._dialog.MessageShowDialog
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {
    abstract val layoutId: Int
    protected lateinit var binding: T

    @Inject
    lateinit var dataStore: DataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
    }

    protected fun checkSessionTimeOut(error: NetworkError): Boolean {
        return if (error == NetworkError.CLIENT_FORBIDDEN) {
            MessageShowDialog(
                this,
                "Session Expired",
                "Your session has been expired",
                isShowCancelButton = false,
                onOkayButtonClick = {
                    //sessionOutRedirection()
                }
            ).showDialog()
            true
        } else {
            false
        }
    }

    protected fun goToPlayStore() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName"))
        startActivity(intent)
        finish()
    }
}