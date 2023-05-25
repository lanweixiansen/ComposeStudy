package com.example.lib_home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.lib_home.data.ItemData
import com.example.lib_home.databinding.HomeItemLayoutBinding

class ItemAdapter: BaseQuickAdapter<ItemData, ItemAdapter.VH>() {
    class VH(
        parent: ViewGroup,
        val binding: HomeItemLayoutBinding = HomeItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: VH, position: Int, item: ItemData?) {
        with(holder.binding) {
//            tvNumber.text = "${position.inc()}"
//            tvContent.text = item?.content

        }
    }

    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): VH {
        return VH(parent)
    }
}