package com.example.lib_news

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lib_base.BaseFragment
import com.example.lib_news.adapter.AnimAdapter
import com.example.lib_news.data.AnimBean
import com.example.lib_news.databinding.NewsFragmentNewsBinding
import com.therouter.TheRouter

class NewsFragment : BaseFragment<NewsFragmentNewsBinding>() {
    private val mAdapter = AnimAdapter()
    override fun initView() {
        with(mBinding) {
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
}