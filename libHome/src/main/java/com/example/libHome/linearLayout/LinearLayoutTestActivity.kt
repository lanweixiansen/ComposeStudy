package com.example.libHome.linearLayout

import com.therouter.router.Route
import com.example.lib_base.BaseActivity
import com.example.lib_base.utils.RouteConsts
import com.example.lib_home.databinding.HomeActivityLinearlayoutTestBinding

@Route(path = RouteConsts.HOME_LINEARLAYOUT)
class LinearLayoutTestActivity : BaseActivity<HomeActivityLinearlayoutTestBinding>() {

    override fun initView() {
    }

    override fun initDate() {

    }

    override fun initListener() {
        super.initListener()
        mBinding.btnAddSport.setOnClickListener {
            mBinding.sportViews.addFragmentView()
        }
        mBinding.btnCheckView.setOnClickListener {
            mBinding.sportViews.checkViewCount()
        }
        mBinding.btnHideView.setOnClickListener {
            mBinding.sportViews.hideFragmentView()
        }
        mBinding.btnReAddView.setOnClickListener {
            mBinding.sportViews.reAddFragmentView()
        }
        mBinding.btnRemoveView.setOnClickListener {
            mBinding.sportViews.removeFragmentView()
        }
    }

}