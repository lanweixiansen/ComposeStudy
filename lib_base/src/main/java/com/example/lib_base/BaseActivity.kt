package com.example.lib_base

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ContentFrameLayout
import androidx.core.view.WindowInsetsControllerCompat
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.BarUtils
import com.example.lib_base.ext.saveAs
import com.example.lib_base.ext.saveAsUnChecked
import com.example.lib_base.utils.ScreenShotListenManager
import com.example.uilibrary.uiUtils.addMarginToEqualStatusBar
import com.example.uilibrary.widget.LoadingDialog
import com.therouter.TheRouter
import org.greenrobot.eventbus.EventBus
import java.lang.reflect.ParameterizedType

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {
    lateinit var mBinding: VB
    private var mLoading: LoadingDialog? = null
    private var screenShotListenManager: ScreenShotListenManager? = null
    private var isHasScreenShotListener = false

    override fun onCreate(savedInstanceState: Bundle?) {
        beforeOnCreated()
        super.onCreate(savedInstanceState)
        val type = javaClass.genericSuperclass
        val vbClass: Class<VB> = type!!.saveAs<ParameterizedType>().actualTypeArguments[0].saveAs()
        val method = vbClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
        mBinding = method.invoke(this, layoutInflater)!!.saveAsUnChecked()
        setContentView(mBinding.root)
        if (useEventBus() && !EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
        //截屏监听
        screenShotListenManager = ScreenShotListenManager.newInstance(this)
        initStatus()
        TheRouter.inject(this)
        initView()
        initDate()
        initListener()
        initObserver()
    }

    open fun beforeOnCreated() {}

    private fun initStatus() {
        BarUtils.transparentStatusBar(window)
        setStatusBarTextColor(isLight = false)
        if (addTopMargin()) {
            findViewById<ContentFrameLayout>(android.R.id.content).addMarginToEqualStatusBar()
        }
    }

    fun showLoading() {
        mLoading ?: run {
            mLoading = LoadingDialog(this)
        }
        if (mLoading?.isShowing == false)
            mLoading?.show()
    }

    fun disLoading() {
        mLoading?.dismiss()
    }

    override fun onDestroy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
        mLoading?.dismiss()
        super.onDestroy()
    }


    abstract fun initView()

    abstract fun initDate()

    open fun initListener() {}

    open fun initObserver() {}
    open fun useEventBus() = false
    open fun addTopMargin() = true


    fun setStatusBarTextColor(isLight: Boolean = false) {
        // 计算颜色亮度
//        val luminanceValue = ColorUtils.calculateLuminance(color)
        WindowInsetsControllerCompat(window, window.decorView).let { controller ->
            controller.isAppearanceLightStatusBars = !isLight
//            if (color == Color.TRANSPARENT) {
//                // 如果是透明颜色就默认设置成黑色
//                controller.isAppearanceLightStatusBars = true
//            } else {
//                // 通过亮度来决定字体颜色是黑还是白
//                controller.isAppearanceLightStatusBars = luminanceValue >= 0.5
//            }
        }
    }


    /**
     * 监听
     */
    open fun startScreenShotListen() {
        if (!isHasScreenShotListener && screenShotListenManager != null) {
            screenShotListenManager?.setListener { imagePath ->
                val path: String = imagePath
                Log.d("msg", "BaseActivity -> onShot: 获得截图路径：$imagePath")
                val bitmap =
                    BitmapFactory.decodeResource(resources, com.example.uilibrary.R.mipmap.img8)
                val addBitmap: Bitmap =
                    screenShotListenManager!!.concatBitmap(this@BaseActivity, imagePath, bitmap)
                // 此处只要分享这个合成的Bitmap图片就行了
                BaseTestDialog(this, addBitmap).show()
            }
            screenShotListenManager?.startListen()
            isHasScreenShotListener = true
        }
    }

    /**
     * 停止监听
     */
    open fun stopScreenShotListen() {
        if (isHasScreenShotListener && screenShotListenManager != null) {
            screenShotListenManager?.stopListen()
            isHasScreenShotListener = false
        }
    }
}