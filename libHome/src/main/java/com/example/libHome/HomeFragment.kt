package com.example.libHome

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.QuickAdapterHelper
import com.example.libHome.adapter.ItemAdapter
import com.example.libHome.bottomSheetDialog.BottomShareDialog
import com.example.libHome.data.itemData
import com.example.libHome.net.viewModel.HomeViewModel
import com.example.lib_base.BaseFragment
import com.example.lib_home.databinding.HomeFragmentHomeBinding
import com.example.uilibrary.widget.HeaderAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<HomeFragmentHomeBinding>() {
    private lateinit var mAdapter: ItemAdapter
    private lateinit var mHelper: QuickAdapterHelper
    private val mViewModel by viewModels<HomeViewModel>()

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
        mViewModel.getBanner()
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
                BottomShareDialog(requireContext()).show()
            } else {
                ARouter.getInstance().build(bean?.route).navigation()
            }
        }
    }
}