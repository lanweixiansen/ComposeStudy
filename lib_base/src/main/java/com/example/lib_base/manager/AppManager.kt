package com.example.lib_base.manager

import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.example.lib_base.ext.saveAs
import com.example.lib_base.interfaces.RouteServer
import com.example.lib_base.utils.RouteConsts

object AppManager {


    fun goLogin() {
        ARouter.getInstance().build(RouteConsts.SERVER_LOGIN)
            .navigation()
            .saveAs<RouteServer>()
            .goLogin()
    }

    fun getContext(): Context? {
        return ARouter.getInstance().build(RouteConsts.SERVER_APP)
            .navigation()
            .saveAs<RouteServer>()
            .getContext()
    }


}