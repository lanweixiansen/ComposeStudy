package com.example.lib_base.interfaces

import android.content.Intent
import com.example.lib_base.utils.RouteConsts
import com.therouter.TheRouter

interface RouteServer {

    fun getLibName(): String  = ""

    fun goLogin() {
        TheRouter.build(RouteConsts.LOGIN_LOGIN)
            .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            .navigation()
    }

    fun startFlutterActivity() {}
}