package com.example.libHome.arouter

import android.content.Context
import android.util.Log
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.enums.RouteType
import com.alibaba.android.arouter.facade.template.IInterceptor
import com.alibaba.android.arouter.launcher.ARouter
import com.example.lib_base.interfaces.RouteServer
import com.example.lib_base.utils.RouteConsts

@Interceptor(priority = 1, name = "测试拦截器")
class TestInterceptor : IInterceptor {
    private var mContext: Context? = null

    override fun init(context: Context?) {
        mContext = context
    }

    override fun process(postcard: Postcard?, callback: InterceptorCallback?) {
        if (postcard?.type == RouteType.ACTIVITY) {
            when (postcard.path) {
                RouteConsts.HOME_ROUTER_INTERCEPTOR_ACTIVITY -> {
                    Log.d("TestInterceptor", "process:  -> 跳转中处理事件")
                    callback?.onContinue(postcard)
                }

                RouteConsts.HOME_ROUTER_INTERCEPTOR2_ACTIVITY -> {
                    (ARouter.getInstance().build(RouteConsts.SERVER_LOGIN)
                        .navigation() as RouteServer).goLogin()
                    callback?.onInterrupt(RuntimeException("请先登录"))
                }

                else -> {
                    callback?.onContinue(postcard)
                }
            }
        } else {
            callback?.onContinue(postcard)
        }
    }
}