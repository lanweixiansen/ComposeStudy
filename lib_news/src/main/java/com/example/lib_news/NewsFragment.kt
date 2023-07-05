package com.example.lib_news

import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.BarUtils
import com.example.lib_base.BaseFragment
import com.example.lib_news.adapter.AnimAdapter
import com.example.lib_news.data.AnimBean
import com.example.lib_news.databinding.NewsFragmentNewsBinding
import com.example.lib_news.databinding.NewsFragmentNewsStubBinding
import com.therouter.TheRouter

class NewsFragment : BaseFragment<NewsFragmentNewsStubBinding>() {
    private val mAdapter = AnimAdapter()
    private lateinit var mBind: NewsFragmentNewsBinding

    override fun initView() {
        BarUtils.addMarginTopEqualStatusBarHeight(mBinding.viewStub)
        mBind = NewsFragmentNewsBinding.bind(mBinding.viewStub.inflate())
        with(mBind) {
            rvAnim.layoutManager = LinearLayoutManager(context)
            rvAnim.adapter = mAdapter
        }
    }

    override fun initDate() {
        mAdapter.submitList(AnimBean.itemData)
    }

    override fun initListener() {
        super.initListener()
        mAdapter.setOnItemClickListener { adapter, _, position ->
            adapter.getItem(position)?.route?.let {
                if (it.isNotBlank()) {
                    showFragment(it)
                }
            }
        }
    }

    private fun showFragment(it: String) {
        TheRouter.build(it).navigation()
    }

    override fun needDelay(): Boolean {
        return true
    }
}