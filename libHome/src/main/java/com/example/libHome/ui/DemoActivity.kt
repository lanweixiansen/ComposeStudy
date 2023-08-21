package com.example.libHome.ui

import com.example.lib_base.BaseActivity
import com.example.lib_base.utils.RouteConsts
import com.example.lib_home.R
import com.example.lib_home.databinding.HomeActivityDemoBinding
import com.therouter.router.Route

@Route(path = RouteConsts.HOME_ROUTER_DEMO_ACTIVITY)
class DemoActivity : BaseActivity<HomeActivityDemoBinding>() {
    override fun initView() {
        supportFragmentManager.beginTransaction().add(R.id.fragment, TitleFragment()).commit()
    }

    override fun initDate() {

    }

    override fun addTopMargin(): Boolean {
        return false
    }


}