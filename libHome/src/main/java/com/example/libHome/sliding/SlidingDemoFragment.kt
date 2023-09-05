package com.example.libHome.sliding

import com.example.lib_base.BaseFragment
import com.example.lib_home.R
import com.example.lib_home.databinding.HomeFragmentDemoSlidingBinding
import com.petterp.floatingx.util.createFx

class SlidingDemoFragment : BaseFragment<HomeFragmentDemoSlidingBinding>() {
    private var mOnClick: (() -> Unit)? = null

    override fun initView() {
        mBinding.btnBack.setOnClickListener {
            mOnClick?.invoke()
        }
    }

    override fun initDate() {
        SlidingUtils.showSliding(this, SlidingTestView(requireContext()))
    }

    fun setOnBackClickListener(onClick: () -> Unit) {
        mOnClick = onClick
    }
}