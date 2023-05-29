package com.example.lib_square

import android.os.CountDownTimer
import com.example.lib_base.BaseActivity
import com.example.lib_base.ext.toast
import com.example.lib_square.bean.JobInfoBean
import com.example.lib_square.databinding.SquareActivityJobTimeBinding

class JobTimeActivity : BaseActivity<SquareActivityJobTimeBinding>() {
    private var mJobInfoBean: JobInfoBean? = null
    private var mDayMoney = 0f // 每天平均工资
    private var mDayWorkTime = 0f // 每天平均工时(秒)
    private var mJobTimer: CountDownTimer? = null

    companion object {
        const val WORKING_DAY = 21.75f
    }

    override fun initView() {
        "摸鱼启动！".toast(this)
    }

    override fun initDate() {
        mJobInfoBean = intent.getSerializableExtra("JOB_INFO") as JobInfoBean?
        mJobInfoBean?.let {
            mDayMoney = it.money / WORKING_DAY
            mDayWorkTime = (it.getEndTimer() - it.startTime) * 3600
            startTimer()
        }
    }

    private fun startTimer() {
        mJobTimer?.cancel()
        if (mDayWorkTime <= 0f) return
        var hour: Int
        var fen: Int
        var miao: Int
        mJobTimer = object : CountDownTimer((mDayWorkTime * 1000L).toLong(), 1000L) {
            override fun onTick(time: Long) {
                hour = (time / 1000 / 3600).toInt()
                fen = (time / 1000 / 60 - hour * 60).toInt()
                miao = (time / 1000 - hour * 3600 - fen * 60).toInt()
                mBinding.tvHour.text = if (hour < 10) "0$hour" else "$hour"
                mBinding.tvMinute.text = if (fen < 10) "0$fen" else "$fen"
                mBinding.tvSeconds.text = if (miao < 10) "0$miao" else "$miao"
            }

            override fun onFinish() {
                "打卡下班！".toast(this@JobTimeActivity)
            }

        }
        mJobTimer?.start()
    }
}