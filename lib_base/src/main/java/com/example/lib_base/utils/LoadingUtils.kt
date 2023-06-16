package com.example.lib_base.utils

import com.blankj.utilcode.util.ActivityUtils
import com.example.uilibrary.widget.LoadingDialog

object LoadingUtils {
    private var mLoading: LoadingDialog? = null

    fun showLoading() {
        mLoading ?: run {
            mLoading = LoadingDialog(ActivityUtils.getTopActivity())
        }
        if (mLoading?.isShowing == false)
            mLoading?.show()
    }

    fun disLoading() {
        mLoading?.dismiss()
    }
}