package com.example.libHome.sliding

import com.example.lib_base.BaseFragment
import com.example.lib_home.databinding.HomeFragmentDemoSlidingBinding
import com.example.spk.sliding.SlidingUtils

class SlidingDemoFragment : BaseFragment<HomeFragmentDemoSlidingBinding>() {
    override fun initView() {

    }

    override fun initDate() {
        SlidingUtils.showSliding(this, SlidingTestView(requireContext(), "Fragment"))
    }
}