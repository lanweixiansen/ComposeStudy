package com.example.lib_base.interfaces

import android.content.Context
import android.content.Intent
import com.alibaba.android.arouter.facade.template.IProvider
import com.alibaba.android.arouter.launcher.ARouter
import com.example.lib_base.utils.RouteConsts

interface RouteServer : IProvider {

    fun getLibName(): String

    fun goLogin() {
        ARouter.getInstance().build(RouteConsts.LOGIN_LOGIN)
            .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            .navigation()
    }

    fun getContext(): Context? {
        return null
    }
}