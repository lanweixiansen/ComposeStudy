package com.example.libHome.therouter

/**
 * 预处理服务，跳转前处理
 */
//@Route(path = "/aa/bb")
//class PretreatmentServiceImpl : PretreatmentService {
//    private var mContext: Context? = null
//
//    override fun init(context: Context?) {
//        mContext = context
//    }
//
//    override fun onPretreatment(context: Context?, postcard: Navigator?): Boolean {
//        return if (postcard?.path == RouteConsts.HOME_ROUTER_INTERCEPTOR3_ACTIVITY) {
//            "请先登录".toast(mContext!!)
//            (TheRouter.build(RouteConsts.SERVER_LOGIN)
//                .navigation() as RouteServer).goLogin()
//            false
//        }else {
//            true
//        }
//    }
//}