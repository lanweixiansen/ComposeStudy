package com.example.libHome.ui

import android.view.View
import com.example.lib_base.BaseScrollTitleFragment
import com.example.lib_home.databinding.HomeFragmentTitleBinding
import com.example.uilibrary.uiUtils.viewBinding

class TitleFragment : BaseScrollTitleFragment() {
    private val binding: HomeFragmentTitleBinding by viewBinding()
    override fun setContentView(): View {
        return binding.root
    }

    override fun setTitle(): String {
        return "这是一个标题"
    }
}