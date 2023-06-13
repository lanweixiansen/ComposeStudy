package com.example.libHome.arouter

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.lib_base.BaseActivity
import com.example.lib_base.utils.RouteConsts
import com.example.lib_home.databinding.HomeActivityInterceptorTest1Binding

@Route(path = RouteConsts.HOME_ROUTER_INTERCEPTOR_ACTIVITY)
class InterceptorTestActivity1: BaseActivity<HomeActivityInterceptorTest1Binding>() {

    override fun initView() {

    }

    override fun initDate() {

    }
}