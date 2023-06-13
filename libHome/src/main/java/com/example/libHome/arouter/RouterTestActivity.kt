package com.example.libHome.arouter

import androidx.core.app.ActivityOptionsCompat
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.lib_base.BaseActivity
import com.example.lib_base.interfaces.RouteServer
import com.example.lib_base.utils.RouteConsts
import com.example.lib_home.databinding.HomeActivityArouterTestBinding

@Route(path = RouteConsts.HOME_ROUTER_ACTIVITY)
class RouterTestActivity : BaseActivity<HomeActivityArouterTestBinding>() {
    @JvmField
    @Autowired(name = RouteConsts.SERVER_HOME)
    var routeServer: RouteServer? = null

    override fun initView() {
        mBinding.topBar.setTitle(routeServer?.getLibName())
    }

    override fun initDate() {}

    override fun initListener() {
        super.initListener()
        // Arouter跳转加转场动画
        mBinding.btnSearch.setOnClickListener {
            val activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                mBinding.etSearch,
                "search_view"
            )
            ARouter.getInstance().build(RouteConsts.HOME_ROUTER_SEARCH_ACTIVITY)
                .withOptionsCompat(activityOptionsCompat)
                .navigation(this)
        }
        mBinding.btnInterceptor1.setOnClickListener {
            ARouter.getInstance().build(RouteConsts.HOME_ROUTER_INTERCEPTOR_ACTIVITY).navigation()
        }
        mBinding.btnInterceptor2.setOnClickListener {
            ARouter.getInstance().build(RouteConsts.HOME_ROUTER_INTERCEPTOR2_ACTIVITY).navigation()
        }
        mBinding.btnInterceptor3.setOnClickListener {
            ARouter.getInstance().build(RouteConsts.HOME_ROUTER_INTERCEPTOR3_ACTIVITY).navigation()
        }
    }
}