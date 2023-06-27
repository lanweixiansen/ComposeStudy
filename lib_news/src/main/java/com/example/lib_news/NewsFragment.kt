package com.example.lib_news

import com.example.lib_base.BaseFragment
import com.example.lib_base.interfaces.RouteServer
import com.example.lib_news.databinding.NewsFragmentNewsBinding
import com.therouter.TheRouter

class NewsFragment : BaseFragment<NewsFragmentNewsBinding>() {
    override fun initView() {
        mBinding.newsTextview.text = TheRouter.get(RouteServer::class.java)?.getLibName()
    }

    override fun initDate() {

    }
}