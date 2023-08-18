package com.example.lib_base

import android.view.View
import com.example.lib_base.databinding.BaseFragmentScrollTitleBinding

abstract class BaseScrollTitleFragment : BaseFragment<BaseFragmentScrollTitleBinding>() {

    override fun initView() {
        mBinding.content.addView(setContentView())
        mBinding.topBar.title = setTitle()
    }

    override fun initDate() {}

    abstract fun setContentView(): View

    abstract fun setTitle(): String
}