package com.example.lib_base.widget

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseSingleItemAdapter
import com.example.lib_base.databinding.BaseAdapterHeaderLayputBinding

class HeaderAdapter : BaseSingleItemAdapter<Any, HeaderAdapter.VM>() {

    class VM(
        parent: ViewGroup,
        val binding: BaseAdapterHeaderLayputBinding = BaseAdapterHeaderLayputBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: VM, item: Any?) {
        holder.binding.lottieView.playAnimation()
    }

    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): VM {
        return VM(parent)
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)
        (holder as? VM)?.binding?.lottieView?.cancelAnimation()
    }
}