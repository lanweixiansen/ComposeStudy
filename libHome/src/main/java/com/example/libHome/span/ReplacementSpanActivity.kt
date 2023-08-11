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
        mTextSpan?.addTextAroundSpan(this, com.example.uilibrary.R.mipmap.img1)
        mTextSpan?.created()
    }

    override fun initDate() {}

    override fun initListener() {
        super.initListener()
        with(mBinding) {
            btnAddIcon.setOnClickListener {
                mTextSpan?.addIconTextSpan(
                    this@ReplacementSpanActivity,
                    com.example.uilibrary.R.color.color_FFB12742,
                    "置顶"
                )?.created()
            }
            btnAddImg.setOnClickListener {
                mTextSpan?.addImgSpan(this@ReplacementSpanActivity, R.mipmap.gn_live_label_vip)
                    ?.created()
            }
            btnRemove.setOnClickListener {
                mTextSpan?.previous()
            }
        }
    }
}