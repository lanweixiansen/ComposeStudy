package com.example.lib_news

import com.example.lib_base.BaseFragment
import com.example.lib_base.utils.RouteConsts
import com.example.lib_news.databinding.NewsFragmentNewsBinding
import com.example.lib_news.databinding.NewsFragmentNewsStubBinding
import com.therouter.router.Route

@Route(path = RouteConsts.NEWS_ROUTE_NEWS_FRAGMENT)
class NewsFragment : BaseFragment<NewsFragmentNewsStubBinding>() {

    private lateinit var mBind: NewsFragmentNewsBinding

    override fun initView() {
        mBind = NewsFragmentNewsBinding.bind(mBinding.viewStub.inflate())
    }

    override fun initDate() {}

}