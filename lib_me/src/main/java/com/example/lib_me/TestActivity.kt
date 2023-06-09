package com.example.lib_me

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.lib_base.BaseActivity
import com.example.lib_base.utils.RouteConsts.ROUTE_ME_TEST
import com.example.lib_me.databinding.MeActivityTestBinding

@Route(path = ROUTE_ME_TEST)
class TestActivity : BaseActivity<MeActivityTestBinding>() {
    override fun initView() {

    }

    override fun initDate() {

    }
}