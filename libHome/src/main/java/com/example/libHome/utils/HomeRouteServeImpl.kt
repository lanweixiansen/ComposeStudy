package com.example.libHome.utils

import com.example.lib_base.interfaces.RouteServer
import com.therouter.inject.ServiceProvider


@ServiceProvider(returnType = RouteServer::class)
class HomeRouteServeImpl : RouteServer {
    override fun getLibName(): String {
        return "libHome"
    }
}