package com.example.lib_square

import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.isVisible
import com.example.lib_base.BaseFragment
import com.example.lib_base.ext.toExFloat
import com.example.lib_base.ext.toExInt
import com.example.lib_base.ext.toast
import com.example.lib_square.bean.JobInfoBean
import com.example.lib_square.databinding.SquareFragmentSquareBinding
import com.example.lib_square.databinding.SquareFragmentSquareStubBinding
import com.example.uilibrary.uiUtils.onClick
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.therouter.TheRouter

class SquareFragment : BaseFragment<SquareFragmentSquareStubBinding>() {
    private lateinit var mBind: SquareFragmentSquareBinding
    private var mStartTime = 0f
    private var mEndTime = 0f

    override fun initView() {
        mBind = SquareFragmentSquareBinding.bind(mBinding.viewStub.inflate())
    }

    override fun initDate() {}

    override fun initListener() {
        mBind.btnJob.setOnClickListener {
            val info = checkInput {
                return@setOnClickListener
            }
            TheRouter.build("/libHome/JobTimeActivity").withObject("bean", info).navigation()
        }
        mBind.switchView.setOnSwitchCheckedStateChangeListener {
            mBind.xiu.isVisible = it
        }
        mBinding.etStartTime.onClick {
            showTimeDialog(mBinding.etStartTime)
        }
        mBinding.etEndTime.onClick {
            showTimeDialog(mBinding.etEndTime)
        }
    }

    private inline fun checkInput(checkError: () -> Unit): JobInfoBean {
        with(mBinding) {
            if (etMoney.text.isNullOrBlank()) {
                "你钱呢".toast()
                checkError()
            }
            if (etStartTime.text.isNullOrBlank()) {
                "你啥时候开始上班啊".toast()
                checkError()
            }
            if (etEndTime.text.isNullOrBlank()) {
                "你不下班啊".toast()
                checkError()
            }
            if (mBind.etReleaseTime.text.isNullOrBlank()) {
                "你不休息啊".toast()
            }
            return JobInfoBean(
                money = etMoney.text.toString().toExInt(),
                startTime = mStartTime,
                endTime = mEndTime,
                releaseTime = mBind.etReleaseTime.text.toString().toExFloat()
            )
        }
    }


    private fun showTimeDialog(etView: AppCompatEditText) {
        val materialTimePickerBuilder = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
        materialTimePickerBuilder.setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
        val materialTimePicker = materialTimePickerBuilder.build()
        materialTimePicker.show(childFragmentManager, "fragment_tag")
        materialTimePicker.addOnPositiveButtonClickListener { dialog: View? ->
            val newHour = materialTimePicker.hour
            val newMinute = materialTimePicker.minute
            etView.setText(if (newMinute < 10) "$newHour:0$newMinute" else "$newHour:$newMinute")
            if (etView == mBinding.etEndTime) {
                mEndTime = (newHour + newMinute / 60f)
            } else {
                mStartTime = (newHour + newMinute / 60f)
            }
        }
    }
}


