package com.example.libHome.intentSetting

import android.content.Intent
import android.graphics.PixelFormat
import android.hardware.input.InputManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import com.therouter.router.Route
import com.example.lib_base.BaseActivity
import com.example.lib_base.ext.saveAs
import com.example.lib_base.utils.RouteConsts
import com.example.lib_home.R
import com.example.lib_home.databinding.HomeActivityIntentSettingBinding


@Route(path = RouteConsts.HOME_ROUTER_INTENT_SETTING_ACTIVITY)
class IntentSettingActivity : BaseActivity<HomeActivityIntentSettingBinding>() {
    private var view: View? = null

    override fun initView() {
        mBinding.btnSetting.setOnClickListener {
            if (!Settings.canDrawOverlays(this)) {
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:$packageName")
                )
                startActivityForResult(intent, 10)
            } else {
                // 已经获得了SYSTEM_ALERT_WINDOW权限，执行相关操作
                startActivity(Intent(Settings.ACTION_SETTINGS))
                showAlertDialog()
            }
        }
    }

    override fun initDate() {}


    private fun showAlertDialog() {
        view = LayoutInflater.from(this)
            .inflate(R.layout.home_dialog_intent_setting_tips, null)
        val layoutParams = WindowManager.LayoutParams()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        }
        layoutParams.flags = (WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        layoutParams.format= PixelFormat.RGBA_8888
        layoutParams.gravity = Gravity.CENTER
        val windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        windowManager.addView(view, layoutParams)
        view?.findViewById<Button>(R.id.btn_close)?.setOnClickListener {
            windowManager.removeView(view)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 10) {
            startActivity(Intent(Settings.ACTION_SETTINGS))
            showAlertDialog()
        }
    }
}