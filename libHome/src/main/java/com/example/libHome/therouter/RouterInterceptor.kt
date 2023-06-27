package com.example.libHome.therouter

import com.example.lib_base.ext.toast
import com.example.lib_base.manager.AppManager
import com.example.lib_base.utils.RouteConsts
import com.therouter.router.RouteItem
import com.therouter.router.addRouterReplaceInterceptor
import com.therouter.router.interceptor.RouterReplaceInterceptor

object RouterInterceptor {

    fun addLoginInterceptor() {
        addRouterReplaceInterceptor(object : RouterReplaceInterceptor() {
            override fun replace(routeItem: RouteItem?): RouteItem? {
                if (routeItem?.path == RouteConsts.HOME_ROUTER_INTERCEPTOR2_ACTIVITY) {
                    "请先登录".toast()
                    AppManager.goLogin()
                    return null
                }
                return routeItem
            }
        })
    }
}