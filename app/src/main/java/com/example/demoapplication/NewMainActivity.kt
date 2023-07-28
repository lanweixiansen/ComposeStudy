package com.example.demoapplication

import android.view.KeyEvent
import com.example.demoapplication.appTask.ApplicationTask
import com.example.demoapplication.databinding.ActivityNewMainBinding
import com.example.demoapplication.navigation.AppNavigation
import com.example.libHome.therouter.RouterInterceptor
import com.example.lib_base.BaseActivity
import com.example.lib_base.ext.addMarginToNavigationBar
import com.example.lib_base.manager.AppData
import com.therouter.router.Route
import io.flutter.embedding.android.FlutterFragment

@Route(path = "/app/NewMainActivity")
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
        initStatusBar()
        ApplicationTask.initFlutterChannel(this)
    }

    private fun initStatusBar() {
        mBinding.bottomView.addMarginToNavigationBar()
    }

    override fun initDate() {}

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 最小化到桌面
            moveTaskToBack(true)
            // 退出APP
//            AppExit.onBackPressed(this)
        }
        return true
    }

    override fun onPostResume() {
        supportFragmentManager.findFragmentByTag("MeFragment")?.let {
            (it as? FlutterFragment)?.onPostResume()
        }
        super.onPostResume()
    }

    override fun onUserLeaveHint() {
        supportFragmentManager.findFragmentByTag("MeFragment")?.let {
            (it as? FlutterFragment)?.onUserLeaveHint()
        }
        super.onUserLeaveHint()
    }

    override fun onBackPressed() {
        supportFragmentManager.findFragmentByTag("MeFragment")?.let {
            (it as? FlutterFragment)?.onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
        if (mBinding.bottomView.isMeChecked()) {
            if (AppData.isLogin()) {
                mBinding.bottomView.checkMe()
            } else {
                mBinding.bottomView.checkHome()
            }
        }
        setStatusBarTextColor()
    }

    override fun onDestroy() {
        AppNavigation.finishInit()
        super.onDestroy()
    }

    override fun addTopMargin() = false
}