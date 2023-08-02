package com.example.uilibrary.widget

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseSingleItemAdapter
import com.example.uilibrary.databinding.LayoutCustomRefreshFooterBinding

class FooterAdapter : BaseSingleItemAdapter<Any, FooterAdapter.VM>() {

    class VM(
        parent: ViewGroup,
        binding: LayoutCustomRefreshFooterBinding = LayoutCustomRefreshFooterBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: VM, item: Any?) {

    }

    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): VM {
        return VM(parent)
    }
}