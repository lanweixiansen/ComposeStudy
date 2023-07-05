package com.example.demoapplication

import android.content.Intent
import android.graphics.Color
import android.view.KeyEvent
import androidx.core.view.WindowCompat
import com.blankj.utilcode.util.BarUtils
import com.example.demoapplication.databinding.ActivityNewMainBinding
import com.example.demoapplication.navigation.AppNavigation
import com.example.libHome.therouter.RouterInterceptor
import com.example.lib_base.BaseActivity
import com.example.lib_base.ext.AppExit
import io.flutter.embedding.android.FlutterFragment

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
    }

    private fun initStatusBar() {
        window.statusBarColor = Color.TRANSPARENT
        setStatusBarTextColor(isLight = false)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        BarUtils.addMarginTopEqualStatusBarHeight(mBinding.fragment)
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
}