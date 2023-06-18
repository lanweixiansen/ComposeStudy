package com.example.demoapplication

import android.app.Application
import android.util.Log
import com.alibaba.android.arouter.launcher.ARouter
import com.example.uilibrary.widget.CustomRefreshHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout

class MyApplication: Application() {
    override fun onCreate() {
        Log.e("MyApplication", "initSdk1: ")
        super.onCreate()
        initSdk()
    }

    private fun initSdk() {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, _ ->
            CustomRefreshHeader(context)
        }
        if (BuildConfig.DEBUG) {
            Log.e("MyApplication", "initSdk2: ")
            ARouter.openLog()
            ARouter.openDebug()
        }
        Log.e("MyApplication", "initSdk3: ")
        ARouter.init(this)
    }

    fun getContext() = this
}