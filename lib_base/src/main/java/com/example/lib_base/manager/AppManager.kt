package com.example.lib_base.manager

import android.app.Application
import com.example.lib_base.interfaces.RouteServer
import com.therouter.TheRouter

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

    fun isAgreePrivacy() = true
}