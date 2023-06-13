package com.example.libHome.arouter

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.enums.RouteType
import com.alibaba.android.arouter.facade.service.PretreatmentService
import com.alibaba.android.arouter.launcher.ARouter
import com.example.lib_base.ext.toast
import com.example.lib_base.interfaces.RouteServer
import com.example.lib_base.utils.RouteConsts

/**
 * 预处理服务，跳转前处理
 */
@Route(path = "/aa/bb")
class PretreatmentServiceImpl : PretreatmentService {
    private var mContext: Context? = null

    override fun init(context: Context?) {
        mContext = context
    }

    override fun onPretreatment(context: Context?, postcard: Postcard?): Boolean {
        return if (postcard?.path == RouteConsts.HOME_ROUTER_INTERCEPTOR3_ACTIVITY) {
            "请先登录".toast(mContext!!)
            (ARouter.getInstance().build(RouteConsts.SERVER_LOGIN)
                .navigation() as RouteServer).goLogin()
            false
        }else {
            true
        }
    }
}