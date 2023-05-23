package com.example.demoapplication

import android.app.Application
import com.example.lib_base.widget.CustomRefreshHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, _ ->
            CustomRefreshHeader(context)
        }
    }
}