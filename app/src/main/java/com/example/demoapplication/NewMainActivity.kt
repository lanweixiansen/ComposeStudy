package com.example.demoapplication

import com.example.demoapplication.databinding.ActivityNewMainBinding
import com.example.demoapplication.navigation.AppNavigation
import com.example.libHome.therouter.RouterInterceptor
import com.example.lib_base.BaseActivity

class NewMainActivity : BaseActivity<ActivityNewMainBinding>() {

    override fun initView() {
        // 添加TheRouter拦截器
        RouterInterceptor.addLoginInterceptor()
        RouterInterceptor.addRouterInterceptor()
        // Fragment相关
        AppNavigation.init(supportFragmentManager)
        mBinding.bottomView.setTabClickListener {
            AppNavigation.checkedFragment(it)
        }
    }

    override fun initDate() {

    }

    override fun onBackPressed() {
        // 最小化到桌面
        moveTaskToBack(true)
        // 退出APP
//        AppExit.onBackPressed(this)
    }
}