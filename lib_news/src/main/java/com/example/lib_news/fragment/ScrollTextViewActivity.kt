package com.example.lib_news.fragment

import com.example.lib_base.BaseActivity
import com.example.lib_base.utils.RouteConsts
import com.example.lib_news.databinding.NewsActivityScrollTextviewBinding
import com.therouter.router.Route

@Route(path = RouteConsts.NEWS_ROUTE_SCROLL_TEXT_FRAGMENT)
class ScrollTextViewActivity : BaseActivity<NewsActivityScrollTextviewBinding>() {
    override fun initView() {
        lifecycle.addObserver(mBinding.text1)
    }

    override fun initDate() {

    }

    override fun initListener() {
        super.initListener()
        mBinding.btnChange.setOnClickListener {
            mBinding.text1.setTextNum(mBinding.etNum.text.toString().toInt())
            mBinding.text2.setText(mBinding.etNum.text.toString())
        }
    }
}