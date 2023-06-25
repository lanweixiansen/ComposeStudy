package com.example.lib_base.manager

import android.app.Application
import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.example.lib_base.ext.saveAs
import com.example.lib_base.interfaces.RouteServer
import com.example.lib_base.utils.RouteConsts

object AppManager {
    private lateinit var app: Application

    fun init(application: Application) {
        this.app = application
    }

    fun goLogin() {
        ARouter.getInstance().build(RouteConsts.SERVER_LOGIN)
            .navigation()
            .saveAs<RouteServer>()
            .goLogin()
    }

    fun getApplicationContext(): Application {
        return app
    }
}