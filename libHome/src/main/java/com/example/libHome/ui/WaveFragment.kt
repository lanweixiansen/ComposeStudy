package com.example.libHome.ui

import com.example.lib_base.BaseFragment
import com.example.lib_home.databinding.HomeFragmentWaveBinding

class WaveFragment: BaseFragment<HomeFragmentWaveBinding>() {
    private var progress = 0
    override fun initView() {
        mBinding.homeButton7.setOnClickListener {
            progress -= 5
            mBinding.wave.setProgress(progress)
        }
        mBinding.homeButton8.setOnClickListener {
            progress += 5
            mBinding.wave.setProgress(progress)
        }
        lifecycle.addObserver(mBinding.wave)
    }

    override fun initDate() {

    }
}