package com.example.demoapplication

import android.view.KeyEvent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.demoapplication.appTask.AGREE_PRIVACY
import com.example.demoapplication.appTask.EngineBindings
import com.example.demoapplication.appTask.initFlutterChannel
import com.example.demoapplication.databinding.ActivityNewMainBinding
import com.example.demoapplication.navigation.AppNavigation
import com.example.lib_base.BaseActivity
import com.example.lib_base.ext.showPrivacyDialog
import com.example.lib_base.manager.AppData
import com.example.lib_base.manager.AppManager
import com.therouter.TheRouter
import com.therouter.router.Route
import io.flutter.embedding.android.FlutterFragment

@Route(path = "/app/NewMainActivity")
class MainActivity : BaseActivity<ActivityNewMainBinding>() {
    private val mainBindings: EngineBindings by lazy {
        EngineBindings(activity = this, entrypoint = "main")
    }
    private val mFlutterFragment: FlutterFragment? by lazy {
        supportFragmentManager.findFragmentByTag("MeFragment") as FlutterFragment?
    }

    override fun beforeOnCreated() {
        installSplashScreen()
    }

    override fun initView() {
        AppManager.setTime2(System.currentTimeMillis())
        privacyCheck()
        initFlutterChannel(mainBindings)
        mainBindings.attach()
        AppNavigation.init(supportFragmentManager)
        mBinding.bottomView.setTabClickListener {
            AppNavigation.checkedFragment(it)
        }
    }


    private fun privacyCheck() {
        if (!AppData.isAgreePrivacy()) {
            showPrivacyDialog(this,
                onSuccess = {
                    TheRouter.runTask(AGREE_PRIVACY)
                }, onRefuse = {
                    finishAfterTransition()
                })
        }
    }

    override fun initDate() {}

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 最小化到桌面
            moveTaskToBack(true)
            // 退出APP
//            AppExit.onBackPressed(this)
            mFlutterFragment?.onBackPressed()
        }
        return true
    }

    override fun onPostResume() {
        mFlutterFragment?.onPostResume()
        super.onPostResume()
    }

    override fun onUserLeaveHint() {
        mFlutterFragment?.onUserLeaveHint()
        super.onUserLeaveHint()
    }

    override fun onResume() {
        initFlutterChannel(mainBindings)
        super.onResume()
        setStatusBarTextColor()
        if (mBinding.bottomView.isMeChecked()) {
            if (AppData.isLogin()) {
                mBinding.bottomView.checkMe()
            } else {
                mBinding.bottomView.checkHome()
            }
        }
    }

    override fun onDestroy() {
        AppNavigation.finishInit()
        super.onDestroy()
        mainBindings.detach()
    }

    override fun addTopMargin() = false
}