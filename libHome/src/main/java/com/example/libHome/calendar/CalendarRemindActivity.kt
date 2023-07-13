package com.example.libHome.calendar

import android.Manifest
import android.content.pm.PackageManager.PERMISSION_GRANTED
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.lib_base.BaseActivity
import com.example.lib_base.utils.RouteConsts
import com.example.lib_home.databinding.HomeActivityCalendarRemindBinding
import com.therouter.router.Route
import java.util.Calendar


@Route(path = RouteConsts.HOME_ROUTER_CALENDAR_REMIND_ACTIVITY)
class CalendarRemindActivity : BaseActivity<HomeActivityCalendarRemindBinding>() {
    private var mTeacherId = ""
    private var mContent = ""

    override fun initView() {
        checkCalendarPermissions()
    }

    override fun initDate() {

    }

    override fun initListener() {
        super.initListener()
        mBinding.homeAdd.setOnClickListener {
            if (!checkCalendarPermissions()) return@setOnClickListener
            getSelectInfo()
            val mCalendar: Calendar = Calendar.getInstance()
            mCalendar.timeInMillis = System.currentTimeMillis() + 4 * 60 * 1000
            val start: Long = mCalendar.time.time
            mCalendar.timeInMillis = start + 2 * 60 * 1000
            val end: Long = mCalendar.time.time
            CalendarReminderUtils.addCalendarEvent(
                this,
                mTeacherId,
                "$mContent 开启了直播",
                "$mContent 开启了直播，快去直播间围观吧！",
                start,
                end,
                1,
                false
            )
        }
        mBinding.homeDelete.setOnClickListener {
            getSelectInfo()
            CalendarReminderUtils.deleteCalendarEvent(this, mTeacherId)
        }
    }

    private val CALENDAR_PERMISSION_REQUEST_CODE = 100

    private fun checkCalendarPermissions(): Boolean {
        val readPermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR)
        val writePermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR)
        if (!(readPermission == PERMISSION_GRANTED && writePermission == PERMISSION_GRANTED)) {
            requestCalendarPermissions()
            return false
        }
        return true
    }

    private fun requestCalendarPermissions() {
        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.READ_CALENDAR,
                Manifest.permission.WRITE_CALENDAR
            ), CALENDAR_PERMISSION_REQUEST_CODE
        )
    }

    private fun getSelectInfo() {
        when {
            mBinding.homeRadiobutton.isChecked -> {
                mTeacherId = "1"
                mContent = "老师1"
            }

            mBinding.homeRadiobutton2.isChecked -> {
                mTeacherId = "2"
                mContent = "老师2"
            }

            mBinding.homeRadiobutton3.isChecked -> {
                mTeacherId = "3"
                mContent = "老师3"
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CALENDAR_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PERMISSION_GRANTED) {
                // 权限已授予，可以执行日历读写操作
            } else {
                // 用户拒绝了权限请求，需要处理相应的逻辑
            }
        }
    }
}