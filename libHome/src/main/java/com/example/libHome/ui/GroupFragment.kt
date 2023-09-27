package com.example.libHome.ui

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.example.libHome.adapter.BannerDBAdapter
import com.example.lib_base.BaseFragment
import com.example.lib_home.databinding.HomeFragmentGroupBinding
import com.example.libnet.room.entry.HomeBannerEntry
import com.example.uilibrary.uiUtils.dp2px
import com.example.uilibrary.widget.StackCardPageTransformer

class GroupFragment : BaseFragment<HomeFragmentGroupBinding>() {
    override fun initView() {
        mBinding.viewPager.adapter = MyPagerAdapter()
        val s = StackCardPageTransformer.Build()
            .setTranslationOffset(dp2px(36f))
            .setAlphaOffset(1f)
            .create(mBinding.viewPager)
        mBinding.viewPager.setPageTransformer(true, s)

        mBinding.viewPager2.adapter = BannerDBAdapter().also {
            it.submitList(
                listOf(
                    HomeBannerEntry(1, "", ""),
                    HomeBannerEntry(1, "", "")
                )
            )
        }
        mBinding.viewPager2.offscreenPageLimit = 2
    }

    override fun initDate() {

    }


    class MyPagerAdapter : PagerAdapter() {
        override fun getCount(): Int = Int.MAX_VALUE

        override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`


        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view = TestView(container.context)
            container.addView(view)
            return view
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View?)
        }
    }
}