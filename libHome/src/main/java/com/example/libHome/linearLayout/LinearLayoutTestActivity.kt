package com.example.libHome.linearLayout

import com.alibaba.android.arouter.facade.annotation.Route
import com.example.lib_base.BaseActivity
import com.example.lib_base.utils.RouteConsts
import com.example.lib_home.databinding.HomeActivityLinearlayoutTestBinding

@Route(path = RouteConsts.AR_LINEARLAYOUT)
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