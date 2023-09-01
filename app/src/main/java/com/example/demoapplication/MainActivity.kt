package com.example.demoapplication

import android.view.KeyEvent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.example.demoapplication.appTask.AGREE_PRIVACY
import com.example.demoapplication.appTask.EngineBindings
import com.example.demoapplication.appTask.initFlutterChannel
import com.example.demoapplication.databinding.ActivityNewMainBinding
import com.example.demoapplication.databinding.ActivityNewMainStubBinding
import com.example.demoapplication.navigation.AppNavigation
import com.example.lib_base.BaseActivity
import com.example.lib_base.ext.showPrivacyDialog
import com.example.lib_base.manager.AppData
import com.example.lib_base.manager.AppManager
import com.example.uilibrary.uiUtils.addMarginToNavigationBar
import com.example.uilibrary.uiUtils.toGone
import com.example.uilibrary.uiUtils.toVisible
import com.therouter.TheRouter
import com.therouter.router.Route
import io.flutter.embedding.android.FlutterFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Route(path = "/app/NewMainActivity")
class MainActivity : BaseActivity<ActivityNewMainStubBinding>() {
    private val mainBindings: EngineBindings by lazy {
        EngineBindings(activity = this, entrypoint = "main")
    }
    private val mFlutterFragment: FlutterFragment? by lazy {
        supportFragmentManager.findFragmentByTag("MeFragment") as FlutterFragment?
    }
    private lateinit var mBind: ActivityNewMainBinding
    private var mIsMainLoad = false

    override fun beforeOnCreated() {
        installSplashScreen()
    }

    override fun initView() {
        AppManager.setTime2(System.currentTimeMillis())
        privacyCheck()
        mBinding.tvJumpAdv.setOnClickListener {
            showMainView()
        }
        lifecycleScope.launch {
            delay(500)
            mBind = ActivityNewMainBinding.bind(mBinding.viewStub.inflate())
            initMainView()
        }
        initFlutterChannel(mainBindings)
        mainBindings.attach()
    }

    private fun initMainView() {
        // Fragment初始化
        mIsMainLoad = true
        AppNavigation.init(supportFragmentManager)
        mBind.bottomView.setTabClickListener {
            AppNavigation.checkedFragment(it)
        }
        initStatusBar()
    }

    private fun privacyCheck() {
        if (!AppData.isAgreePrivacy()) {
            showPrivacyDialog(this,
                onSuccess = {
                    TheRouter.runTask(AGREE_PRIVACY)
                    showMainView()
                }, onRefuse = {
                    finishAfterTransition()
                })
        } else {
            startAdvTimer()
        }
    }

    private fun startAdvTimer() {
        var i = 3
        mBinding.tvJumpAdv.toVisible()
        mBinding.lottie.playAnimation()
        lifecycleScope.launch {
            while (i > 0) {
                mBinding.tvJumpAdv.text = ("跳过广告：$i s")
                delay(1000)
                i--
            }
            showMainView()
        }
    }

    private fun showMainView() {
        mBinding.lottie.toGone()
        mBinding.tvJumpAdv.toGone()
    }

    private fun initStatusBar() {
        mBind.bottomView.addMarginToNavigationBar()
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
        if (mIsMainLoad) {
            if (mBind.bottomView.isMeChecked()) {
                if (AppData.isLogin()) {
                    mBind.bottomView.checkMe()
                } else {
                    mBind.bottomView.checkHome()
                }
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