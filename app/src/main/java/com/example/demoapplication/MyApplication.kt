package com.example.demoapplication

import android.app.Application
import com.therouter.TheRouter
import com.example.lib_base.manager.AppManager
import com.example.uilibrary.widget.CustomRefreshHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initSdk()
    }

    private fun initSdk() {
        AppManager.init(this)
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, _ ->
            CustomRefreshHeader(context)
        }
    }
}