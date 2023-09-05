package com.example.libHome.sliding

import com.example.lib_base.BaseActivity
import com.example.lib_base.utils.RouteConsts
import com.example.lib_home.databinding.HomeSlidingDemoActivityBinding
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
                    SlidingTestView(this@SlidingDemoActivity)
                )
            }
            homeApp.setOnClickListener {

            }
            homeFragment.setOnClickListener {
                supportFragmentManager.beginTransaction().add(fragment.id, mFragment).commit()
            }
            homeView.setOnClickListener {

            }
        }
    }

    override fun initDate() {}
}