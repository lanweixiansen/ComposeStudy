package com.example.libHome.sliding

import com.example.lib_base.BaseActivity
import com.example.lib_base.manager.AppManager
import com.example.lib_base.utils.RouteConsts
import com.example.lib_home.databinding.HomeSlidingDemoActivityBinding
import com.example.spk.sliding.SlidingUtils
import com.example.uilibrary.uiUtils.onClick
import com.example.uilibrary.uiUtils.toGone
import com.example.uilibrary.uiUtils.toVisible
import com.therouter.router.Route

@Route(path = RouteConsts.HOME_ROUTER_SLIDING_DEMO_ACTIVITY)
class SlidingDemoActivity : BaseActivity<HomeSlidingDemoActivityBinding>() {
    private var mFragment: SlidingDemoFragment? = null

    override fun initView() {
        with(mBinding) {
            homeActivity.setOnClickListener {
                SlidingUtils.showSliding(
                    this@SlidingDemoActivity,
                    SlidingTestView(this@SlidingDemoActivity, "Activity")
                )
            }
            homeApp.setOnClickListener {
                SlidingUtils.showApplicationSliding(
                    this@SlidingDemoActivity,
                    AppManager.getApplicationContext(),
                    SlidingTestView(this@SlidingDemoActivity, "App")
                )
            }
            homeFragment.onClick {
                mFragment = SlidingDemoFragment()
                supportFragmentManager.beginTransaction().add(fragment.id, mFragment!!).commit()
                btnRemove.toVisible()
            }
            homeView.setOnClickListener {
                viewSliding.toVisible()
                btnRemoveView.toVisible()
            }
            btnRemove.onClick {
                btnRemove.toGone()
                supportFragmentManager.beginTransaction().remove(mFragment!!).commit()
                mFragment = null
            }
            btnRemoveView.setOnClickListener {
                viewSliding.toGone()
                btnRemoveView.toGone()
            }
        }
    }

    override fun initDate() {}
}