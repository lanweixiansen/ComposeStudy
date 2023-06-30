package com.example.lib_news.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.lib_news.data.AnimBean
import com.example.lib_news.databinding.NewsRvItemAnimBinding

class AnimAdapter : BaseQuickAdapter<AnimBean.AnimData, AnimAdapter.VM>() {

    class VM(
        parent: ViewGroup,
        val mBinding: NewsRvItemAnimBinding = NewsRvItemAnimBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    ) : RecyclerView.ViewHolder(mBinding.root)

    override fun onBindViewHolder(holder: VM, position: Int, item: AnimBean.AnimData?) {
        holder.mBinding.tvTitle.text = item?.title
    }

    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): VM {
        return VM(parent)
    }

}