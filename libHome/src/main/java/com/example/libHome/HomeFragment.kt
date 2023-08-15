package com.example.libHome

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.QuickAdapterHelper
import com.example.libHome.adapter.BannerAdapter
import com.example.libHome.adapter.ItemAdapter
import com.example.libHome.bottomSheetDialog.BottomShareDialog
import com.example.libHome.data.itemData
import com.example.libHome.net.viewModel.HomeViewModel
import com.example.lib_base.BaseFragment
import com.example.lib_home.databinding.HomeFragmentHomeBinding
import com.example.lib_home.databinding.HomeFragmentHomeStubBinding
import com.example.uilibrary.uiUtils.dp2px
import com.example.uilibrary.uiUtils.onHeaderMoving
import com.example.uilibrary.widget.FooterAdapter
import com.example.uilibrary.widget.HeaderAdapter
import com.therouter.TheRouter

class HomeFragment : BaseFragment<HomeFragmentHomeStubBinding>() {
    private lateinit var mAdapter: ItemAdapter
    private val mBannerAdapter = BannerAdapter()
    private lateinit var mHelper: QuickAdapterHelper
    private val mViewModel by viewModels<HomeViewModel>()
    private lateinit var mBind: HomeFragmentHomeBinding

    override fun initView() {
        mBind = HomeFragmentHomeBinding.bind(mBinding.homeStub.inflate())
        with(mBind) {
            mAdapter = ItemAdapter()
            mHelper = QuickAdapterHelper.Builder(mAdapter)
                .build()
                .addAfterAdapter(mBannerAdapter)
                .addAfterAdapter(FooterAdapter())
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
        mBind.smartRefresh.setOnRefreshListener {
            loadData()
        }
        mBind.smartRefresh.onHeaderMoving {
            mBind.lottieView.layoutParams = mBind.lottieView.layoutParams.apply {
                height = dp2px(220) + it
            }
        }
        mAdapter.setOnItemClickListener { adapter, _, position ->
            val bean = adapter.getItem(position)
            if (bean?.route.isNullOrBlank()) {
                BottomShareDialog(requireContext()).show()
            } else {
                TheRouter.build(bean?.route).navigation()
            }
        }
    }

    private fun loadData() {
        mViewModel.getBanner()
    }

    override fun initObserver() {
        super.initObserver()
        mViewModel.mComplete.observe(viewLifecycleOwner) {
            mBind.smartRefresh.finishRefresh()
        }
        mViewModel.mBanner.observe(viewLifecycleOwner) {
            mBannerAdapter.submitList(it)
        }
    }
}