package com.example.lib_square

import android.content.Intent
import com.example.lib_base.BaseFragment
import com.example.lib_base.ext.toExFloat
import com.example.lib_base.ext.toExInt
import com.example.lib_base.ext.toast
import com.example.lib_square.bean.JobInfoBean
import com.example.lib_square.databinding.SquareFragmentSquareBinding
import com.example.lib_square.databinding.SquareFragmentSquareStubBinding

class SquareFragment : BaseFragment<SquareFragmentSquareStubBinding>() {
    private lateinit var mBind: SquareFragmentSquareBinding
    override fun initView() {
        mBind = SquareFragmentSquareBinding.bind(mBinding.viewStub.inflate())
    }

    override fun initDate() {}

    override fun needDelay(): Boolean {
        return true
    }

    override fun initListener() {
        super.initListener()
        mBind.btnJob.setOnClickListener {
            val info = checkInput {
                return@setOnClickListener
            }
            val intent = Intent(context, JobTimeActivity::class.java)
            intent.putExtra("JOB_INFO", info)
            startActivity(intent)
        }
    }

    private inline fun checkInput(checkError: () -> Unit): JobInfoBean {
        with(mBind) {
            if (etMoney.text.isNullOrBlank()) {
                "你钱呢".toast(requireContext())
                checkError()
            }
            if (etStartTime.text.isNullOrBlank()) {
                "你啥时候开始上班啊".toast(requireContext())
                checkError()
            }
            if (etEndTime.text.isNullOrBlank()) {
                "你不下班啊".toast(requireContext())
                checkError()
            }
            if (etReleaseTime.text.isNullOrBlank()) {
                "你不休息啊".toast(requireContext())
            }
            return JobInfoBean(
                money = etMoney.text.toString().toExInt(),
                startTime = etStartTime.text.toString().toExFloat(),
                endTime = etEndTime.text.toString().toExFloat(),
                releaseTime = etReleaseTime.text.toString().toExFloat()
            )
        }
    }

}


