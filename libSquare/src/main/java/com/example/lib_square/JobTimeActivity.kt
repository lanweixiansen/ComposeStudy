package com.example.lib_square

import android.graphics.Color
import android.os.CountDownTimer
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.example.lib_base.BaseActivity
import com.example.lib_base.ext.toast
import com.example.lib_square.bean.JobInfoBean
import com.example.lib_square.databinding.SquareActivityJobTimeBinding
import com.example.lib_square.utils.MoneyUtils
import com.example.lib_square.utils.TimeUtils.getPresentTime
import kotlinx.coroutines.launch

class JobTimeActivity : BaseActivity<SquareActivityJobTimeBinding>() {
    private var mJobInfoBean: JobInfoBean? = null

    /**
     * 每天平均工时(秒)
     */
    private var mDayAverageWorkTime = 0f

    /**
     * 平均每毫秒获得的工资
     */
    private var mMillisecondMoneyMoney = 0f


    private var mJobTimer: CountDownTimer? = null

    private var mMoneyTimer: CountDownTimer? = null

    companion object {
        const val TAG = "JobTimeActivity"
        const val WORKING_DAY = 21.75f
    }

    override fun initView() {
        window.statusBarColor = Color.TRANSPARENT
        setStatusBarTextColor(true)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        "摸鱼启动！".toast()
    }

    override fun initDate() {
        mJobInfoBean = intent.getSerializableExtra("JOB_INFO") as JobInfoBean?
        lifecycleScope.launch {
            mJobInfoBean?.let {
                MoneyUtils.init(it)
                timeCalculation(it)
            }
        }
    }

    /**
     * 倒计时准备
     */
    private fun timeCalculation(it: JobInfoBean) {
        mDayAverageWorkTime = (it.getEndTimer() - it.startTime) * 3600000
        val surplusTime = mDayAverageWorkTime - getPresentTime(it.startTime)
        mMillisecondMoneyMoney = MoneyUtils.getMillisecondMoney()
        startTimer(surplusTime)
        startMoneyTimer(surplusTime)
    }


    /**
     * 开始倒计时
     * 计算剩余时间 & 已赚钱
     */
    private fun startTimer(surplusTime: Float) {
        mJobTimer?.cancel()
        if (surplusTime <= 0f) return
        var hour: Int
        var fen: Int
        var miao: Int
        mJobTimer = object : CountDownTimer((surplusTime).toLong(), 1) {
            override fun onTick(time: Long) {
                hour = (time / 1000 / 3600).toInt()
                fen = (time / 1000 / 60 - hour * 60).toInt()
                miao = ((time - hour * 3600000 - fen * 60000) / 100).toInt()
                mBinding.tvHour.text = if (hour < 10) "0$hour" else "$hour"
                mBinding.tvMinute.text = if (fen < 10) "0$fen" else "$fen"
                mBinding.tvSeconds.text = if (miao < 100) "0$miao" else "$miao"
            }

            override fun onFinish() {
                mBinding.loOver.playAnimation()
                "打卡下班！".toast()
            }
        }
        mJobTimer?.start()
    }


    private fun startMoneyTimer(surplusTime: Float) {
        mMoneyTimer?.cancel()
        if (surplusTime <= 0 || mMillisecondMoneyMoney <= 0)
            return
        var haveMoney =
            MoneyUtils.getHaveObtainedMoney(getPresentTime(mJobInfoBean?.startTime ?: 0f))
        mMoneyTimer = object : CountDownTimer(surplusTime.toLong(), 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                mBinding.tvMoney.text = ("￥${String.format("%.2f", haveMoney)}")
                haveMoney += mMillisecondMoneyMoney
            }

            override fun onFinish() {}
        }
        mMoneyTimer?.start()
    }

    override fun onDestroy() {
        mJobTimer?.cancel()
        mMoneyTimer?.cancel()
        super.onDestroy()
    }
}