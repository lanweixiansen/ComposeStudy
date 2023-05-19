package com.example.lib_base

import android.app.Dialog
import android.content.Context

class LoadingDialog(context: Context): Dialog(context, R.style.base_loading_dialog) {

    init {
        setContentView(R.layout.base_dialog_loading)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
    }
}