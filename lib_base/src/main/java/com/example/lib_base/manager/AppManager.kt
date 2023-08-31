package com.example.lib_base.manager

import android.app.Application
import android.util.Log
import com.example.lib_base.BuildConfig
import com.example.lib_base.ext.toast
import com.example.lib_base.interfaces.RouteServer
import com.therouter.TheRouter

object AppManager {
    private lateinit var app: Application
    private var s1 = 0L

    fun init(application: Application) {
        this.app = application
    }

    fun goLogin() {
        TheRouter.get(RouteServer::class.java)?.goLogin()
    }

    fun getApplicationContext(): Application {
        return app
    }

    fun getApplicationId(): String = BuildConfig.APPLICATION_ID
    fun setTime1(currentTimeMillis: Long) {
        s1 = currentTimeMillis
    }

    fun setTime2(currentTimeMillis: Long) {
        "冷启动耗时 -》 ${currentTimeMillis - s1}".toast()
    }
}