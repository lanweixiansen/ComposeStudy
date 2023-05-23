package com.example.lib_home

import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.QuickAdapterHelper
import com.example.lib_base.BaseFragment
import com.example.lib_base.widget.HeaderAdapter
import com.example.lib_home.adapter.ItemAdapter
import com.example.lib_home.data.itemData
import com.example.lib_home.databinding.HomeFragmentHomeBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeFragment: BaseFragment<HomeFragmentHomeBinding>() {
    private lateinit var mAdapter: ItemAdapter

    override fun initView() {
        with(mBinding) {
            mAdapter = ItemAdapter()
            val helper = QuickAdapterHelper.Builder(mAdapter)
                .setLeadingLoadStateAdapter(HeaderAdapter())
                .build()
            recycleView.adapter = helper.adapter
            recycleView.layoutManager = LinearLayoutManager(context)
        }

    }

    override fun initDate() {
        mAdapter.submitList(itemData)
    }

    override fun initListener() {
        super.initListener()
        mBinding.smartRefresh.setOnRefreshListener {
            lifecycleScope.launch {
                showLoading()
                delay(2_000)
                mBinding.smartRefresh.finishRefresh()
                disLoading()
            }
        }
    }
}