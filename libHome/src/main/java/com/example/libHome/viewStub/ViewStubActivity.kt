package com.example.libHome.viewStub

import android.content.Intent
import com.therouter.router.Route
import com.example.lib_base.BaseActivity
import com.example.lib_base.utils.RouteConsts
import com.example.lib_home.databinding.HomeActivityViewStubBinding
@Route(path = RouteConsts.HOME_ROUTER_VIEW_STUB_ACTIVITY)
class ViewStubActivity : BaseActivity<HomeActivityViewStubBinding>() {

    override fun initView() {
        mBinding.homeButton.setOnClickListener {
            startActivity(Intent(this, ViewStubTestActivity::class.java))
        }
        mBinding.homeButton2.setOnClickListener {
            startActivity(Intent(this, ViewStubTest2Activity::class.java))
        }
    }

    override fun initDate() {}

}