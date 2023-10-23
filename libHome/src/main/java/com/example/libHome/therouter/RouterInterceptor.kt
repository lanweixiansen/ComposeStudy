package com.example.libHome.therouter

import com.example.lib_base.ext.toast
import com.example.lib_base.utils.RouteConsts
import com.therouter.router.RouteItem
import com.therouter.router.addNavigatorPathFixHandle
import com.therouter.router.addRouterReplaceInterceptor
import com.therouter.router.interceptor.InterceptorCallback
import com.therouter.router.interceptor.NavigatorPathFixHandle
import com.therouter.router.interceptor.PathReplaceInterceptor
import com.therouter.router.interceptor.RouterInterceptor
import com.therouter.router.interceptor.RouterReplaceInterceptor
import com.therouter.router.matchRouteMap
import com.therouter.router.setRouterInterceptor

/**
 * theRouter四种拦截器
 */
object RouterInterceptor {

    /**
     * 路由替换器
     * 常用于未登录不能跳转的页面
     */
    fun addLoginInterceptor() {
        addRouterReplaceInterceptor(object : RouterReplaceInterceptor() {
            override fun replace(routeItem: RouteItem?): RouteItem? {
                if (routeItem?.path == RouteConsts.HOME_ROUTER_INTERCEPTOR2_ACTIVITY) {
                    "请先登录".toast()
                    return matchRouteMap(RouteConsts.LOGIN_LOGIN)
//                    AppManager.goLogin()
//                    return null
                }
                return routeItem
            }
        })
    }

    /**
     * Path修改器
     */
    fun addPathInterceptor() {
        addNavigatorPathFixHandle(object : NavigatorPathFixHandle() {
            override fun fix(path: String?): String? {
                var newPath = path?.replace("xxx", "xx")
                if (path?.contains("xxxxx") == true) {
                    newPath += "xxxx"
                }
                return newPath
            }

            /**
             * 优先级：数字越大优先级越高
             */
            override val priority: Int
                get() = super.priority

        })
    }

    /**
     * Path替换器
     * 与 path修改器不同的是，替换器只有在跳转的时候才会生效，而修改器主要解决通用性问题
     */
    fun addPathReplaceInterceptor() {
        com.therouter.router.addPathReplaceInterceptor(object : PathReplaceInterceptor() {
            override fun replace(path: String?): String? {
                if (path?.equals("xxx") == true) {
                    return "xxx"
                }
                return path
            }
        })
    }

    /**
     * AOP 拦截器，全局只能有一个
     */
    fun addRouterInterceptor() {
        setRouterInterceptor(object : RouterInterceptor {
            override fun process(routeItem: RouteItem, callback: InterceptorCallback) {
                if (routeItem.path == RouteConsts.HOME_ROUTER_INTERCEPTOR3_ACTIVITY) {
                    "未登录无法跳转，请登录".toast()
                } else {
                    callback.onContinue(routeItem)
                }
            }
        })
    }

}