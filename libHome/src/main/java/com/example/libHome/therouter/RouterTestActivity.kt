package com.example.libHome.therouter

import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.lifecycleScope
import com.example.libHome.utils.HomeRouteServeImpl
import com.example.lib_base.BaseActivity
import com.example.lib_base.interfaces.RouteServer
import com.example.lib_base.utils.RouteConsts
import com.example.lib_home.R
import com.example.lib_home.databinding.HomeActivityArouterTestBinding
import com.therouter.TheRouter
import com.therouter.router.Route
import kotlinx.coroutines.launch

@Route(path = RouteConsts.HOME_ROUTER_ACTIVITY)
class RouterTestActivity : BaseActivity<HomeActivityArouterTestBinding>() {
    private val routeServer: RouteServer? = TheRouter.get(HomeRouteServeImpl::class.java)

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
            TheRouter.build(RouteConsts.HOME_ROUTER_SEARCH_ACTIVITY)
                .withOptionsCompat(activityOptionsCompat.toBundle())
                .navigation(this)
        }
        mBinding.btnInterceptor1.setOnClickListener {
            TheRouter.build(RouteConsts.HOME_ROUTER_INTERCEPTOR_ACTIVITY).navigation()
        }
        mBinding.btnInterceptor2.setOnClickListener {
            TheRouter.build(RouteConsts.HOME_ROUTER_INTERCEPTOR2_ACTIVITY).navigation()
        }
        mBinding.btnInterceptor3.setOnClickListener {
            TheRouter.build(RouteConsts.HOME_ROUTER_INTERCEPTOR3_ACTIVITY).navigation()
        }
        mBinding.btnOpenFragment.setOnClickListener {
            supportFragmentManager.beginTransaction().add(
                R.id.fragment,
                TheRouter.build("/http/TheRouterFragment").createFragment<TheRouterFragment>()!!
            ).commit()

        }
        mBinding.btnAction.setOnClickListener {
            TheRouter.build("show_dialog").action()
        }
        mBinding.btnAction2.setOnClickListener {
            lifecycleScope.launch {
                showDialog(this@RouterTestActivity, "弹窗1", "这是弹窗1", "下一个")
                showDialog(this@RouterTestActivity, "弹窗2", "这是弹窗2", "下一个")
                showDialog(this@RouterTestActivity, "弹窗3", "这是弹窗3", "关闭")
            }
        }
    }
}
