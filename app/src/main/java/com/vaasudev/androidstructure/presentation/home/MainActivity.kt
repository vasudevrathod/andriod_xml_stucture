package com.vaasudev.androidstructure.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.vaasudev.androidstructure.BuildConfig
import com.vaasudev.androidstructure.R
import com.vaasudev.androidstructure.data.remote.ApiObject
import com.vaasudev.androidstructure.databinding.ActivityMainBinding
import com.vaasudev.androidstructure.domain.error_handle.NetworkError
import com.vaasudev.androidstructure.domain.error_handle.asNetworkErrorString
import com.vaasudev.androidstructure.domain.response.InitResponse
import com.vaasudev.androidstructure.domain.utility.Status
import com.vaasudev.androidstructure.presentation._base.BaseActivity
import com.vaasudev.androidstructure.presentation._dialog.InitStateDialog
import dagger.hilt.android.AndroidEntryPoint

/**
 * # Created by ~ `V J R`
 * Created on `Wed, 09 Jul 2025`
 */
@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(), View.OnClickListener {

    private val viewModel: HomeViewModel by viewModels()

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        init()
    }

    /** Initial Screen View */
    private fun init() {
        setClickListener()
        observeData()
    }

    /** API call state observer */
    private fun observeData() {
        viewModel.uiState.observe(this) { status ->
            when (status) {
                Status.Loading -> {
                    binding.cpbLoader.visibility = View.VISIBLE
                    binding.btCallAPI.visibility = View.GONE
                }

                is Status.Error<*> -> {
                    binding.cpbLoader.visibility = View.GONE
                    binding.btCallAPI.visibility = View.VISIBLE
                    when (status.error) {
                        is NetworkError -> {
                            if (!checkSessionTimeOut(status.error)) {
                                Toast.makeText(
                                    this,
                                    status.error.asNetworkErrorString(this),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        else -> {}
                    }
                }

                is Status.Success<*> -> {
                    binding.cpbLoader.visibility = View.GONE
                    binding.btCallAPI.visibility = View.VISIBLE
                    when (status.data) {
                        is InitResponse -> {
                            status.data.let { data ->
                                if (data.state != 3) {
                                    manageState(data.message, data.state)
                                } else {
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun manageState(message: String, state: Int) {
        InitStateDialog(
            this,
            message,
            state,
            onOkBtnClick = {
                when (state) {
                    0, 1 -> {
                        goToPlayStore()
                    }

                    2 -> {
                        //finish()
                    }
                }
            }, onCancelBtnClick = {
                if (state == 0) {

                }
            }).showDialog()
    }

    /** On Click Call API*/
    private fun callAPI() {
        viewModel.callInit(
            version = BuildConfig.VERSION_NAME,
            deviceType = ApiObject.DefaultParamValue.DEVICE_TYPE_ANDROID
        )
    }

    /** Initialize All Click Listener */
    private fun setClickListener() {
        binding.btCallAPI.setOnClickListener(this)
    }

    /** Click Action */
    override fun onClick(v: View?) {
        v?.let { view ->
            when (view.id) {
                R.id.btCallAPI -> callAPI()
            }
        }
    }

}