package com.example.libHome

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.lifecycleScope
import com.example.libHome.data.RefreshEvent
import com.example.lib_base.BaseFragment
import com.example.lib_base.utils.TimeUtils
import com.example.lib_home.databinding.HomeFragmentHomeBinding
import com.example.uilibrary.uiUtils.dp2px
import com.example.uilibrary.uiUtils.onHeaderMoving
import com.example.uilibrary.widget.CustomRefreshFooter
import com.scwang.smart.refresh.header.FalsifyHeader
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus

class HomeFragment : BaseFragment<HomeFragmentHomeBinding>() {

    private val tab = listOf("demo", "动画")

    override fun initView() {
        with(mBinding) {
            viewPager.adapter = PagerAdapter(childFragmentManager, tab)
            viewPager.offscreenPageLimit = 0
            tabLayout.setupWithViewPager(viewPager)
        }
        lifecycleScope.launch {
            delay(1000)
            mBinding.lottieView.playAnimation()
        }
    }

    override fun initDate() {
        tab.forEachIndexed { index, s ->
            mBinding.tabLayout.getTabAt(index)?.text = s
        }
    }

    override fun initListener() {
        mBinding.smartRefresh.setRefreshHeader(FalsifyHeader(context))
        mBinding.smartRefresh.setRefreshFooter(CustomRefreshFooter(requireContext()))
        mBinding.smartRefresh.setOnRefreshListener {
            EventBus.getDefault().post(RefreshEvent())
            mBinding.smartRefresh.finishRefresh()
        }
        mBinding.smartRefresh.setOnLoadMoreListener {
            mBinding.smartRefresh.finishLoadMore()
        }
        mBinding.smartRefresh.onHeaderMoving {
            TimeUtils.getTime(1698854194000)
            mBinding.lottieView.layoutParams = mBinding.lottieView.layoutParams.apply {
                height = dp2px(220) + it
            }
            mBinding.lottieView.speed = if (it != 0) 5f else 1f
        }
    }

    class PagerAdapter(fm: FragmentManager, private val tabs: List<String>) :
        FragmentPagerAdapter(fm) {
        override fun getCount() = tabs.size

        override fun getItem(position: Int): Fragment {
            return DemoFragment.newInstance(position)
        }
    }
}