package com.example.demoapplication

import android.view.KeyEvent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.demoapplication.appTask.AGREE_PRIVACY
import com.example.demoapplication.appTask.EngineBindings
import com.example.demoapplication.appTask.initFlutterChannel
import com.example.demoapplication.databinding.ActivityNewMainBinding
import com.example.demoapplication.navigation.AppNavigation
import com.example.libHome.therouter.RouterInterceptor
import com.example.lib_base.BaseActivity
import com.example.lib_base.ext.showPrivacyDialog
import com.example.lib_base.manager.AppData
import com.example.uilibrary.uiUtils.addMarginToNavigationBar
import com.therouter.TheRouter
import com.therouter.router.Route
import io.flutter.embedding.android.FlutterFragment

@Route(path = "/app/NewMainActivity")
class NewMainActivity : BaseActivity<ActivityNewMainBinding>() {
    private val mainBindings: EngineBindings by lazy {
        EngineBindings(activity = this, entrypoint = "main")
    }

    override fun beforeOnCreated() {
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {
            false
        }
    }

    override fun initView() {
        // 添加TheRouter拦截器
        if (!AppData.isAgreePrivacy()) {
            showPrivacyDialog(this,
                onSuccess = {
                    TheRouter.runTask(AGREE_PRIVACY)
                }, onRefuse = {
                    finish()
                })
        }
        RouterInterceptor.addLoginInterceptor()
        RouterInterceptor.addRouterInterceptor()
        // Fragment相关
        AppNavigation.init(supportFragmentManager)
        mBinding.bottomView.setTabClickListener {
            AppNavigation.checkedFragment(it)
        }
        initStatusBar()
        initFlutterChannel(mainBindings)
        mainBindings.attach()
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
        initFlutterChannel(mainBindings)
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
        mainBindings.detach()
    }

    override fun addTopMargin() = false
}