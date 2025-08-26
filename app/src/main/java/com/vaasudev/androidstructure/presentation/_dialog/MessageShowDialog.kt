package com.vaasudev.androidstructure.presentation._dialog

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import com.vaasudev.androidstructure.R
import com.vaasudev.androidstructure.databinding.DialogMessageShowBinding
import com.vaasudev.androidstructure.domain.utility.setDialogTheme

class MessageShowDialog(
    private val context: Context,
    private val title: String? = null,
    private val message: String,
    private val okButtonText: String? = null,
    private val cancelButtonText: String? = null,
    private val onOkayButtonClick: (() -> Unit)? = null,
    private val onCancelButtonClick: (() -> Unit)? = null,
    private val isShowCancelButton: Boolean = true
) : Dialog(context, R.style.DialogTheme) {


    init {
        this.setDialogTheme()
    }

    fun showDialog() {

        val dialogBinding: DialogMessageShowBinding = DataBindingUtil.inflate(
            LayoutInflater.from(context), R.layout.dialog_message_show, null, false)
        this.setContentView(dialogBinding.root)

        dialogBinding.let { binding ->

            if(title != null){
                binding.tvTitle.text = title
            }else{
                binding.tvTitle.visibility = View.GONE
            }

            if(okButtonText != null){
                binding.btOk.text = okButtonText
            }

            if(cancelButtonText != null){
                binding.btCancel.text = cancelButtonText
            }
            if (!isShowCancelButton){
                binding.btCancel.visibility = View.GONE
            }

            binding.tvMessage.text = message
            binding.btOk.setOnClickListener {
                this.dismiss()
                onOkayButtonClick?.invoke()
            }
            binding.btCancel.setOnClickListener {
                this.dismiss()
                onCancelButtonClick?.invoke()
            }
        }

        this.show()
    }

    fun dismissDialog() {
        this.dismiss()
    }
}