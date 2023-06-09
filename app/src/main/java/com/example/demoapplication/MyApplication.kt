package com.example.demoapplication

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.example.lib_base.widget.CustomRefreshHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initSdk()
    }

    private fun initSdk() {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, _ ->
            CustomRefreshHeader(context)
        }
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
    }
}