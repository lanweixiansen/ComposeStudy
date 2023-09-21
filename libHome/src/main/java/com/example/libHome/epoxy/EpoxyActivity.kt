package com.example.libHome.epoxy

import android.content.Context
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.QuickViewHolder
import com.example.lib_base.BaseActivity
import com.example.lib_base.utils.RouteConsts
import com.example.lib_home.R
import com.example.lib_home.databinding.HomeActivityEpoxyBinding
import com.therouter.router.Route

@Route(path = RouteConsts.HOME_ROUTER_EPOXY_ACTIVITY)
class EpoxyActivity : BaseActivity<HomeActivityEpoxyBinding>() {
    private var test = false
    private lateinit var mAdapter: MyAdapter


    private val mContent =
        "张一弛 —— 注册证券投资顾问，高能核心投顾老师。“情绪周期理论”及“市场资金心理学”资深学者。驰骋纵横于市场多年，具有独特的交易体系和操作风格，历经市场数轮牛熊考验。"
    val mList = arrayListOf(
        mContent, mContent, mContent, mContent, mContent, mContent, mContent, mContent,
        mContent, mContent, mContent, mContent, mContent, mContent, mContent, mContent,
        mContent, mContent, mContent, mContent, mContent, mContent, mContent, mContent,
        mContent, mContent, mContent, mContent, mContent, mContent, mContent, mContent,
        mContent, mContent, mContent, mContent, mContent, mContent, mContent, mContent,
    )
    override fun initView() {
        mBinding.rvTest.adapter =  MyAdapter().also { mAdapter = it }
        mBinding.smartRefresh.setOnLoadMoreListener {
            mBinding.smartRefresh.finishLoadMore()
        }
    }

    override fun initDate() {
        mAdapter.submitList(mList)
    }

    class MyAdapter : BaseQuickAdapter<String, QuickViewHolder>() {
        override fun onBindViewHolder(holder: QuickViewHolder, position: Int, item: String?) {
            holder.setText(R.id.tv_content, item)
        }

        override fun onCreateViewHolder(
            context: Context,
            parent: ViewGroup,
            viewType: Int
        ): QuickViewHolder {
            return QuickViewHolder(R.layout.home_rv_item_fold_text, parent)
        }
    }
}