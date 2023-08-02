package com.example.uilibrary.widget

import android.app.Dialog
import android.content.Context
import com.example.uilibrary.R

class LoadingDialog(context: Context) : Dialog(context, R.style.base_loading_dialog) {

    init {
        setContentView(R.layout.base_dialog_loading)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
    }
}