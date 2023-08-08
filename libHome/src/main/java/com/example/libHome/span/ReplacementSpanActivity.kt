package com.example.libHome.span

import com.example.lib_base.BaseActivity
import com.example.lib_base.utils.RouteConsts
import com.example.lib_home.R
import com.example.lib_home.databinding.HomeActivityReplacementSpanBinding
import com.therouter.router.Route

@Route(path = RouteConsts.HOME_ROUTER_REPLACEMENT_SPAN_ACTIVITY)
class ReplacementSpanActivity : BaseActivity<HomeActivityReplacementSpanBinding>() {
    private var mTextSpan: TextSpanUtils? = null
    override fun initView() {
        mTextSpan = mBinding.tvSpan.withTextSpan()
        mTextSpan?.created()
    }

    override fun initDate() {}

    override fun initListener() {
        super.initListener()
        mBinding.btnAddIcon.setOnClickListener {
            mTextSpan?.addIconTextSpan(this, com.example.uilibrary.R.color.color_FFB12742, "置顶")?.created(false)
        }
        mBinding.btnAddImg.setOnClickListener {
            mTextSpan?.addImgSpan(this, R.mipmap.gn_live_label_vip)?.created(false)
        }
    }

}