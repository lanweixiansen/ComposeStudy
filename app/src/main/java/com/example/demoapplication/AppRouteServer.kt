package com.example.demoapplication

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.lib_base.interfaces.RouteServer
import com.example.lib_base.utils.RouteConsts

@Route(path = RouteConsts.SERVER_APP)
class AppRouteServer : RouteServer {
    override fun getLibName(): String {
        return "app"
    }

    override fun init(context: Context?) {

    }

    override fun getApplicationContext(): Context {
        return MyApplication().getContext()
    }

}