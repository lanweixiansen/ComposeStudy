package com.example.lib_base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ContentFrameLayout
import androidx.core.view.WindowInsetsControllerCompat
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.BarUtils
import com.therouter.TheRouter
import com.example.lib_base.ext.saveAs
import com.example.lib_base.ext.saveAsUnChecked
import com.example.uilibrary.widget.LoadingDialog
import org.greenrobot.eventbus.EventBus
import java.lang.reflect.ParameterizedType

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {
    lateinit var mBinding: VB
    private var mLoading: LoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val type = javaClass.genericSuperclass
        val vbClass: Class<VB> = type!!.saveAs<ParameterizedType>().actualTypeArguments[0].saveAs()
        val method = vbClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
        mBinding = method.invoke(this, layoutInflater)!!.saveAsUnChecked()
        setWindowStyle(window)
        setContentView(mBinding.root)
        findViewById<ContentFrameLayout>(android.R.id.content).transitionName = "activity_anim"
        if (useEventBus() && !EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
        TheRouter.inject(this)
        initView()
        initDate()
        initListener()
        initObserver()
    }

    open fun setWindowStyle(window: Window) {}

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

}