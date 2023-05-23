package com.example.lib_base.widget

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.loadState.LoadState
import com.chad.library.adapter.base.loadState.leading.LeadingLoadStateAdapter
import com.example.lib_base.databinding.BaseAdapterHeaderLayputBinding

class HeaderAdapter : LeadingLoadStateAdapter<HeaderAdapter.VM>() {

    class VM(
        parent: ViewGroup,
        val binding: BaseAdapterHeaderLayputBinding = BaseAdapterHeaderLayputBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: VM, loadState: LoadState) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): VM {
        return VM(parent)
    }
}