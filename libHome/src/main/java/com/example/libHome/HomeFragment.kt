package com.example.libHome

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.libHome.data.RefreshEvent
import com.example.lib_base.BaseFragment
import com.example.lib_home.databinding.HomeFragmentHomeBinding
import com.example.lib_home.databinding.HomeFragmentHomeStubBinding
import com.example.uilibrary.uiUtils.dp2px
import com.example.uilibrary.uiUtils.onHeaderMoving
import org.greenrobot.eventbus.EventBus

class HomeFragment : BaseFragment<HomeFragmentHomeStubBinding>() {
    private val mBind: HomeFragmentHomeBinding by lazy {
        HomeFragmentHomeBinding.bind(mBinding.homeStub.inflate())
    }
    private val tab = listOf("demo", "动画")

    override fun initView() {
        with(mBind) {
            viewPager.adapter = PagerAdapter(childFragmentManager, tab)
            viewPager.offscreenPageLimit = 0
            tabLayout.setupWithViewPager(viewPager)
        }
    }

    override fun initDate() {
        tab.forEachIndexed { index, s ->
            mBind.tabLayout.getTabAt(index)?.text = s
        }
    }

    override fun initListener() {
        mBind.smartRefresh.setOnRefreshListener {
            EventBus.getDefault().post(RefreshEvent())
            mBind.smartRefresh.finishRefresh()
        }
        mBind.smartRefresh.onHeaderMoving {
            mBind.lottieView.layoutParams = mBind.lottieView.layoutParams.apply {
                height = dp2px(220) + it
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mBind.lottieView.playAnimation()
    }

    class PagerAdapter(fm: FragmentManager, private val tabs: List<String>) :
        FragmentPagerAdapter(fm) {
        override fun getCount() = tabs.size

        override fun getItem(position: Int): Fragment {
            return DemoFragment.newInstance(position)
        }
    }
}