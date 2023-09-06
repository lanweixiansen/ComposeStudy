package com.example.libHome.sliding

import com.example.lib_base.BaseActivity
import com.example.lib_base.manager.AppManager
import com.example.lib_base.utils.RouteConsts
import com.example.lib_home.databinding.HomeSlidingDemoActivityBinding
import com.example.uilibrary.uiUtils.toGone
import com.example.uilibrary.uiUtils.toVisible
import com.therouter.router.Route

@Route(path = RouteConsts.HOME_ROUTER_SLIDING_DEMO_ACTIVITY)
class SlidingDemoActivity : BaseActivity<HomeSlidingDemoActivityBinding>() {
    private val mFragment by lazy {
        SlidingDemoFragment()
    }

    override fun initView() {
        with(mBinding) {
            homeActivity.setOnClickListener {
                SlidingUtils.showSliding(
                    this@SlidingDemoActivity,
                    SlidingTestView(this@SlidingDemoActivity, "Activity")
                )
            }
            homeApp.setOnClickListener {
                SlidingUtils.showSliding(this@SlidingDemoActivity)
            }
            homeFragment.setOnClickListener {
                supportFragmentManager.beginTransaction().add(fragment.id, mFragment).commit()
                btnRemove.toVisible()
            }
            homeView.setOnClickListener {
                viewSliding.toVisible()
                btnRemoveView.toVisible()
            }
            btnRemove.setOnClickListener {
                btnRemove.toGone()
                supportFragmentManager.beginTransaction().remove(mFragment).commit()
            }
            btnRemoveView.setOnClickListener {
                viewSliding.toGone()
                btnRemoveView.toGone()
            }
        }
    }

    override fun initDate() {}
}