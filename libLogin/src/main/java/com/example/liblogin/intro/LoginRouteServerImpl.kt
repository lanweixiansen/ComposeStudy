package com.example.liblogin.intro

import com.example.lib_base.interfaces.RouteServer
import com.therouter.inject.ServiceProvider


@ServiceProvider(returnType = RouteServer::class)
class LoginRouteServerImpl : RouteServer {
    override fun getLibName(): String {
        return "libLogin"
    }
}