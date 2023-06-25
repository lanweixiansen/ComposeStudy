package com.example.libHome.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.libHome.data.Banner
import com.example.lib_base.utils.loadImage
import com.example.lib_home.databinding.HomeRvItemBannerBinding
import com.example.libnet.room.entry.HomeBannerEntry

class BannerAdapter : BaseQuickAdapter<Banner, BannerAdapter.VH>() {

    class VH(
        parent: ViewGroup,
        val binding: HomeRvItemBannerBinding = HomeRvItemBannerBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: VH, position: Int, item: Banner?) {
        with(holder.binding) {
            ivBanner.loadImage(item?.imagePath)
            tvBanner.text = item?.desc
        }
    }

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): VH {
        return VH(parent)
    }

}

class BannerDBAdapter : BaseQuickAdapter<HomeBannerEntry, BannerDBAdapter.VH>() {

    class VH(
        parent: ViewGroup,
        val binding: HomeRvItemBannerBinding = HomeRvItemBannerBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: VH, position: Int, item: HomeBannerEntry?) {
        with(holder.binding) {
            ivBanner.loadImage(item?.imagePath)
            tvBanner.text = item?.desc
        }
    }

    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): VH {
        return VH(parent)
    }
}