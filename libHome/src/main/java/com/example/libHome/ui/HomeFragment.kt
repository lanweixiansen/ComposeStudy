package com.example.libHome.ui

import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.QuickAdapterHelper
import com.example.libHome.adapter.ItemAdapter
import com.example.libHome.data.itemData
import com.example.lib_base.BaseFragment
import com.example.lib_base.interfaces.RouteServer
import com.example.lib_base.utils.RouteConsts
import com.example.lib_home.databinding.HomeFragmentHomeBinding
import com.example.uilibrary.widget.HeaderAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
class HomeFragment : BaseFragment<HomeFragmentHomeBinding>() {

    private lateinit var mAdapter: ItemAdapter
    private lateinit var mHelper: QuickAdapterHelper

    override fun initView() {
        with(mBinding) {
            mAdapter = ItemAdapter()
            mHelper = QuickAdapterHelper.Builder(mAdapter)
                .build()
                .addBeforeAdapter(HeaderAdapter())
            recycleView.adapter = mHelper.adapter
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
        mAdapter.setOnItemClickListener { adapter, _, position ->
            val bean = adapter.getItem(position)
            if (bean?.route.isNullOrBlank()) {
                SheetDialog(requireContext()).show()
            } else {
                ARouter.getInstance().build(bean?.route).navigation()
            }
        }
    }
}