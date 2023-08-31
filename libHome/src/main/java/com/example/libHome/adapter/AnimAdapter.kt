package com.example.libHome.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.libHome.data.AnimBean
import com.example.lib_home.databinding.HomeRvItemAnimBinding

class AnimAdapter : BaseQuickAdapter<AnimBean.AnimData, AnimAdapter.VM>() {

    class VM(
        parent: ViewGroup,
        val mBinding: HomeRvItemAnimBinding = HomeRvItemAnimBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    ) : RecyclerView.ViewHolder(mBinding.root)

    override fun onBindViewHolder(holder: VM, position: Int, item: AnimBean.AnimData?) {
        holder.mBinding.tvTitle.text = item?.title
        holder.mBinding.animLottie.playAnimation()
    }

    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): VM {
        return VM(parent)
    }


    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)
        (holder as VM).mBinding.animLottie.cancelAnimation()
    }

}