package com.example.libHome.utils

import android.content.Context
import android.content.Intent
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.lib_base.interfaces.RouteServer
import com.example.lib_base.utils.RouteConsts

@Route(path = RouteConsts.SERVER_HOME)
class HomeRouteServeImpl : RouteServer {
    override fun getLibName(): String {
        return "libHome"
    }

    override fun init(context: Context?) {

    }
}