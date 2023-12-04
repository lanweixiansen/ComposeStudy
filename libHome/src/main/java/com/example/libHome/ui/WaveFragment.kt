package com.example.libHome.ui

import android.graphics.Color
import com.example.libHome.adapter.AnimAdapter
import com.example.libHome.data.AnimBean
import com.example.lib_base.BaseFragment
import com.example.lib_home.databinding.ProfileHomePageActivityBinding
import com.example.uilibrary.uiUtils.toGone
import com.example.uilibrary.uiUtils.toVisible
import kotlin.math.abs

class WaveFragment : BaseFragment<ProfileHomePageActivityBinding>() {
    private var progress = 0
    override fun initView() {
        mBinding.rv.adapter = AnimAdapter().also {
            it.submitList(AnimBean.animItemData)
        }

//        mBinding.homeButton7.setOnClickListener {
//            progress -= 5
//            mBinding.wave.setProgress(progress)
//        }
//        mBinding.homeButton8.setOnClickListener {
//            progress += 5
//            mBinding.wave.setProgress(progress)
//        }
//        lifecycle.addObserver(mBinding.wave)

        mBinding.appBarLayout.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            // 折叠状态
            if (abs(verticalOffset) >= appBarLayout.totalScrollRange) {
                mBinding.tvTitle.toVisible()
                mBinding.ivBack.setColorFilter(Color.BLACK)
            } else {
                mBinding.tvTitle.toGone()
                mBinding.ivBack.setColorFilter(Color.WHITE)
            }
        }
    }

    override fun initDate() {}
}