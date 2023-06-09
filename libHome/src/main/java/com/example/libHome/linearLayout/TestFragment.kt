package com.example.libHome.linearLayout

import android.os.CountDownTimer
import com.example.lib_base.BaseFragment
import com.example.lib_base.ext.toast
import com.example.lib_home.databinding.HomeFragmentTestFragmentBinding


class TestFragment : BaseFragment<HomeFragmentTestFragmentBinding>() {
    private var mTimer: CountDownTimer? = null
    override fun initView() {

    }

    override fun initDate() {

    }

    fun setSportData(time: Int) {
        mTimer?.cancel()
        mTimer = object : CountDownTimer(time * 1000L, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                mBinding.tvTime.text = (millisUntilFinished / 1000).toString()
            }

            override fun onFinish() {

            }

        }.start()
    }

    override fun onDestroy() {
        mTimer?.cancel()
        "倒计时结束".toast(requireContext())
        super.onDestroy()
    }
}