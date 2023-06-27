package com.example.lib_base.manager

import android.app.Application
import com.example.lib_base.interfaces.RouteServer
import com.therouter.TheRouter
import com.therouter.router.RouteItem
import com.therouter.router.interceptor.RouterReplaceInterceptor

object AppManager {
    private lateinit var app: Application

    fun init(application: Application) {
        this.app = application
    }

    fun goLogin() {
        TheRouter.get(RouteServer::class.java)?.goLogin()
    }

    fun getApplicationContext(): Application {
        return app
    }
}