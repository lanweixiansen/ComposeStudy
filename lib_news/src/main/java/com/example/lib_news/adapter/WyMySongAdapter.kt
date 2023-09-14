package com.example.lib_news.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.example.lib_news.databinding.NewsRvItemSongBinding
import com.example.lib_news.databinding.NewsRvItemSongTitleBinding
import com.google.android.flexbox.FlexboxLayoutManager

data class WySongEntry(
    val title: String?,
    val songList: List<MySongListBean>?
)

data class MySongListBean(
    val name: String?,
    val canClick: Boolean,
)

class SongAdapter(private val isMySong: Boolean) : BaseQuickAdapter<WySongEntry, SongAdapter.VH>() {
    private var mOnClick: ((MySongListBean) -> Unit)? = null

    class VH(val viewBinding: NewsRvItemSongTitleBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    override fun onBindViewHolder(holder: VH, position: Int, item: WySongEntry?) {
        with(holder.viewBinding) {
            tvTitle.text = item?.title
            rvSongs.layoutManager = FlexboxLayoutManager(context)
            rvSongs.adapter = WySongAdapter(isMySong).also {
                it.submitList(item?.songList)
                it.setOnClickListener { bean ->
                    mOnClick?.invoke(bean)
                }
            }
        }
    }

    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): VH {
        return VH(NewsRvItemSongTitleBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    fun setOnClickListener(onClick: (MySongListBean) -> Unit) {
        mOnClick = onClick
    }
}

class WySongAdapter(private val isMySong: Boolean) :
    BaseQuickAdapter<MySongListBean, WySongAdapter.SongVH>() {
    private var mOnClick: ((MySongListBean) -> Unit)? = null

    class SongVH(val viewBinding: NewsRvItemSongBinding) : RecyclerView.ViewHolder(viewBinding.root)

    override fun onBindViewHolder(holder: SongVH, position: Int, item: MySongListBean?) {
        with(holder.viewBinding) {
            tvSong.text = item?.name
            btn.isSelected = isMySong
            parent.isEnabled = item?.canClick ?: false
            parent.setOnClickListener {
                if (item != null) {
                    mOnClick?.invoke(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): SongVH {
        return SongVH(NewsRvItemSongBinding.inflate(LayoutInflater.from(context), parent, false))
    }


    fun setOnClickListener(onClick: (MySongListBean) -> Unit) {
        mOnClick = onClick
    }
}