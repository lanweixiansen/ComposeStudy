package com.example.libHome

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.QuickAdapterHelper
import com.example.libHome.adapter.BannerAdapter
import com.example.libHome.adapter.ItemAdapter
import com.example.libHome.bottomSheetDialog.SheetDialog
import com.example.libHome.data.itemData
import com.example.libHome.net.HomeApi
import com.example.libHome.net.viewModel.HomeViewModel
import com.example.lib_base.BaseFragment
import com.example.lib_base.ext.toast
import com.example.lib_home.databinding.HomeFragmentHomeBinding
import com.example.libnet.response.requestLiveData
import com.example.libnet.viewModel.createdApi
import com.example.uilibrary.widget.HeaderAdapter
import com.therouter.TheRouter

class HomeFragment : BaseFragment<HomeFragmentHomeBinding>() {
    private lateinit var mAdapter: ItemAdapter
    private val mBannerAdapter = BannerAdapter()
    private lateinit var mHelper: QuickAdapterHelper
    private val mViewModel by viewModels<HomeViewModel>()
    private val mHomeApi = HomeApi::class.java.createdApi()

    override fun initView() {
        with(mBinding) {
            mAdapter = ItemAdapter()
            mHelper = QuickAdapterHelper.Builder(mAdapter)
                .build()
                .addAfterAdapter(mBannerAdapter)
                .addBeforeAdapter(HeaderAdapter())
            recycleView.adapter = mHelper.adapter
            recycleView.layoutManager = LinearLayoutManager(context)
        }
    }

    override fun initDate() {
        mAdapter.submitList(itemData)
        loadData()
    }

    override fun initListener() {
        super.initListener()
        mBinding.smartRefresh.setOnRefreshListener {
            loadData()
        }
        mAdapter.setOnItemClickListener { adapter, view, position ->
            val bean = adapter.getItem(position)
            if (bean?.route.isNullOrBlank()) {
                SheetDialog(requireContext()).show()
            } else {
//                val anim = ActivityOptionsCompat.makeSceneTransitionAnimation(
//                    requireActivity(), view.findViewById(R.id.parent), "activity_anim"
//                )
//                TheRouter.build(bean?.route).withOptionsCompat(anim)
//                    .navigation(requireActivity())
                TheRouter.build(bean?.route).navigation()
            }
        }
    }

    private fun loadData() {
        showLoading()
        requestLiveData(
            showLoading = true,
            requestCall = { mHomeApi.getHomeBanner() },
            onComplete = {
                "加载完成".toast()
                mBinding.smartRefresh.finishRefresh()
                disLoading()
            },
            errorBlock = { errorCode, errorMsg ->
                "加载失败：$errorCode - $errorMsg".toast()
            }).observe(viewLifecycleOwner) {
            mBannerAdapter.submitList(it)
        }

//        mViewModel.getBanner()
//        mViewModel.mBanner.observe(viewLifecycleOwner) {
//            mBannerAdapter.submitList(it)
//        }
//        mViewModel.mComplete.observe(viewLifecycleOwner) {
//            disLoading()
//            mBinding.smartRefresh.finishRefresh()
//        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}