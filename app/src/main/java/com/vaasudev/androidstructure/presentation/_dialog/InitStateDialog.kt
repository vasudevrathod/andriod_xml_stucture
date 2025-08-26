package com.vaasudev.androidstructure.presentation._dialog

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import com.vaasudev.androidstructure.R
import com.vaasudev.androidstructure.databinding.DialogInitStateBinding
import com.vaasudev.androidstructure.domain.utility.setDialogTheme

class InitStateDialog(
    private val context: Context,
    private val message: String,
    private val state: Int,
    private val onOkBtnClick: () -> Unit,
    private val onCancelBtnClick: () -> Unit,
) : Dialog(context, R.style.DialogTheme) {


    init {
        this.setDialogTheme()
    }

    fun showDialog() {

        val dialogBinding: DialogInitStateBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.dialog_init_state, null, false)
        this.setContentView(dialogBinding.root)

        dialogBinding.let { binding ->

            when(state) { // 0 -> Optional Update, 1 -> Force Update, 2 -> Maintenance, 3 -> Regular Flow
                0 -> {
                    binding.tvTitle.text =  "Update"
                    binding.btOk.text = "Update"
                    binding.btCancel.visibility = View.VISIBLE
                }
                1 -> {
                    binding.tvTitle.text =  "Update"
                    binding.btOk.text = "Update"
                    binding.btCancel.visibility = View.GONE
                }
                2 -> {
                    binding.tvTitle.text =  "Maintenance"
                    binding.btOk.text = "Ok"
                    binding.btCancel.visibility = View.GONE
                }
            }

            binding.tvMessage.text = message
            binding.btOk.setOnClickListener {
                this.dismiss()
                onOkBtnClick.invoke()
            }

            binding.btCancel.setOnClickListener {
                this.dismiss()
                onCancelBtnClick.invoke()
            }
        }


        this.show()
    }

    fun dismissDialog() {
        this.dismiss()
    }
}