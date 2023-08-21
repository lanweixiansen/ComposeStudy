package com.example.libHome.ui

import android.view.LayoutInflater
import android.view.View
import com.example.lib_base.BaseScrollTitleFragment
import com.example.lib_home.R
import com.example.lib_home.databinding.HomeFragmentTitleBinding
import com.example.uilibrary.uiUtils.viewBinding

class TitleFragment : BaseScrollTitleFragment() {

    override fun setContentView(): View {
        return LayoutInflater.from(context).inflate(R.layout.home_fragment_title, null, false)
    }

    override fun setTitle(): String {
        return "这是一个标题"
    }
}