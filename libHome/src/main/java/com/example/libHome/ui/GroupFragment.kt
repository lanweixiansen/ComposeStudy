package com.example.libHome.ui

import com.example.lib_base.BaseFragment
import com.example.lib_home.databinding.HomeFragmentGroupBinding
import com.example.uilibrary.uiUtils.onClick
import com.example.uilibrary.uiUtils.toGone
import com.example.uilibrary.uiUtils.toVisible

class GroupFragment: BaseFragment<HomeFragmentGroupBinding>() {
    override fun initView() {
        with(mBinding) {
            homeButton3.onClick { homeGroup.toVisible() }
            homeButton4.onClick { homeGroup.toGone() }
            homeButton5.onClick { homeGroup2.toVisible() }
            homeButton6.onClick { homeGroup2.toGone() }
        }
    }

    override fun initDate() {

    }
}