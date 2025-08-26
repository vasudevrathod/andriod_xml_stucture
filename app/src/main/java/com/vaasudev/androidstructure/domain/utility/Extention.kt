package com.vaasudev.androidstructure.domain.utility

import android.app.Dialog
import android.view.Window
import android.view.WindowManager

fun Dialog.setDialogTheme() {
    this.requestWindowFeature(Window.FEATURE_NO_TITLE)
    this.window?.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    this.setCancelable(false)
    val lp = WindowManager.LayoutParams()
    lp.copyFrom(this.window!!.attributes)
    lp.dimAmount = 0.5f
    lp.width = WindowManager.LayoutParams.MATCH_PARENT
    lp.height = WindowManager.LayoutParams.WRAP_CONTENT
    this.window?.setAttributes(lp)
}