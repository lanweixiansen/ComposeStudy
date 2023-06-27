package com.example.libHome.therouter

//class TestInterceptor {
//
//    private var mContext: Context? = null
//
//    override fun init(context: Context?) {
//        mContext = context
//    }
//
//    override fun process(postcard: Navigator?, callback: InterceptorCallback?) {
//        if (postcard?.type == RouteType.ACTIVITY) {
//            when (postcard.path) {
//                RouteConsts.HOME_ROUTER_INTERCEPTOR_ACTIVITY -> {
//                    Log.d("TestInterceptor", "process:  -> 跳转中处理事件")
//                    callback?.onContinue(postcard)
//                }
//
//                RouteConsts.HOME_ROUTER_INTERCEPTOR2_ACTIVITY -> {
//                    (TheRouter.build(RouteConsts.SERVER_LOGIN)
//                        .navigation() as RouteServer).goLogin()
//                    callback?.onInterrupt(RuntimeException("请先登录"))
//                }
//
//                else -> {
//                    callback?.onContinue(postcard)
//                }
//            }
//        } else {
//            callback?.onContinue(postcard)
//        }
//    }
//
//}