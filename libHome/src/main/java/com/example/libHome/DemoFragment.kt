package com.example.libHome

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.QuickAdapterHelper
import com.example.libHome.adapter.AnimAdapter
import com.example.libHome.adapter.BannerAdapter
import com.example.libHome.adapter.ItemAdapter
import com.example.libHome.bottomSheetDialog.BottomShareDialog
import com.example.libHome.data.AnimBean.itemData
import com.example.libHome.data.RefreshEvent
import com.example.libHome.net.viewModel.HomeViewModel
import com.example.lib_base.BaseFragment
import com.example.lib_base.utils.RouteConsts
import com.example.lib_home.databinding.HomeFragmentDemoBinding
import com.example.uilibrary.widget.FooterAdapter
import com.therouter.TheRouter
import com.therouter.router.Route
import org.greenrobot.eventbus.Subscribe

@Route(path = RouteConsts.HOME_ROUTER_DEMO_FRAGMENT)
class DemoFragment : BaseFragment<HomeFragmentDemoBinding>() {
    private var type: Int = 0
    private val mAnimAdapter = AnimAdapter()
    private lateinit var mAdapter: ItemAdapter
    private lateinit var mHelper: QuickAdapterHelper
    private val mBannerAdapter = BannerAdapter()
    private val mViewModel by viewModels<HomeViewModel>()

    override fun initView() {
        type = arguments?.getInt("type") ?: 0

        mAdapter = ItemAdapter()
        mHelper = QuickAdapterHelper.Builder(mAdapter)
            .build()
            .addAfterAdapter(mBannerAdapter)
            .addAfterAdapter(FooterAdapter())
        with(mBinding.recycleView) {
            layoutManager = LinearLayoutManager(context)
            adapter = when (type) {
                0 -> mHelper.adapter
                1 -> mAnimAdapter
                else -> mAnimAdapter
            }
        }
    }

    override fun initDate() {
        loadData()
    }

    private fun loadData() {
        when (type) {
            0 -> {
                mAdapter.submitList(com.example.libHome.data.itemData)
                mViewModel.getBanner()
            }

            1 -> {
                mAnimAdapter.submitList(itemData)
            }
        }
    }

    override fun initListener() {
        mAnimAdapter.setOnItemClickListener { adapter, _, position ->
            adapter.getItem(position)?.route?.let {
                if (it.isNotBlank()) {
                    TheRouter.build(it).navigation()
                }
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

    override fun initObserver() {
        super.initObserver()
        mViewModel.mBanner.observe(viewLifecycleOwner) {
            mBannerAdapter.submitList(it)
        }
    }

    override fun useEventBus() = true

    @Subscribe(priority = 1)
    fun refresh(event: RefreshEvent) {
        if (event.refresh) {
            loadData()
        }
    }

    companion object {
        fun newInstance(type: Int): DemoFragment {
            val args = Bundle()
            args.putInt("type", type)
            val fragment = DemoFragment()
            fragment.arguments = args
            return fragment
        }
    }
}