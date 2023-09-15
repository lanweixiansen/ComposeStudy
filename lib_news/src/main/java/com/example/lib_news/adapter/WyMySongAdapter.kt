package com.example.lib_news.adapter

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Path
import android.graphics.PathMeasure
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.LinearLayout
import androidx.core.animation.doOnEnd
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseSingleItemAdapter
import com.example.lib_news.databinding.NewsRvItemSongBinding
import com.example.lib_news.databinding.NewsRvItemSongTitleBinding
import com.example.uilibrary.uiUtils.toGone
import com.example.uilibrary.uiUtils.toVisible
import com.google.android.flexbox.FlexboxLayoutManager

data class WySongEntry(
    val title: String?,
    val songList: List<MySongListBean>?
)

data class MySongListBean(
    val name: String?,
    var canClick: Boolean,
    var isAnimItem: Boolean = false
)

private val location = IntArray(2)


// 我的歌单Adapter
class MySongAdapter : BaseSingleItemAdapter<WySongEntry, MySongAdapter.VH>() {
    private var mOnClick: ((MySongListBean, Int) -> Unit)? = null

    class VH(val viewBinding: NewsRvItemSongTitleBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    override fun onBindViewHolder(holder: VH, item: WySongEntry?) {
        with(holder.viewBinding) {
            tvTitle.text = item?.title
            rvSongs.layoutManager = FlexboxLayoutManager(context)
            rvSongs.adapter = WySongAdapter(true).also {
                it.submitList(item?.songList)
                it.setOnClickListener { bean ->
                    mOnClick?.invoke(bean, holder.layoutPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): VH {
        return VH(NewsRvItemSongTitleBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    fun setOnClickListener(onClick: (MySongListBean, Int) -> Unit) {
        mOnClick = onClick
    }
}

//所有歌单Adapter
class SongAdapter : BaseQuickAdapter<WySongEntry, SongAdapter.VH>() {
    private var mOnClick: ((MySongListBean, Int) -> Unit)? = null

    class VH(val viewBinding: NewsRvItemSongTitleBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    override fun onBindViewHolder(holder: VH, position: Int, item: WySongEntry?) {
        with(holder.viewBinding) {
            tvTitle.text = item?.title
            rvSongs.layoutManager = FlexboxLayoutManager(context)
            rvSongs.adapter = WySongAdapter(false).also {
                it.submitList(item?.songList)
                it.setOnClickListener { bean ->
                    mOnClick?.invoke(bean, position)
                }
            }
        }
    }

    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): VH {
        return VH(NewsRvItemSongTitleBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    fun setOnClickListener(onClick: (MySongListBean, Int) -> Unit) {
        mOnClick = onClick
    }
}

// 歌曲Adapter
class WySongAdapter(private val isMySong: Boolean) :
    BaseQuickAdapter<MySongListBean, WySongAdapter.SongVH>() {
    private var mOnClick: ((MySongListBean) -> Unit)? = null

    class SongVH(val viewBinding: NewsRvItemSongBinding) : RecyclerView.ViewHolder(viewBinding.root)

    override fun onBindViewHolder(holder: SongVH, position: Int, item: MySongListBean?) {
        with(holder.viewBinding) {
            tvSong.text = item?.name
            tvSong2.text = item?.name
            btn.isSelected = isMySong
            parent.isEnabled = item?.canClick ?: false
            parent.setOnClickListener {
                if (item != null) {
                    mOnClick?.invoke(item)
                    animLayout.startAnim()
                    item.canClick = false
                    parent.isEnabled = false
                }
            }
            if (item?.isAnimItem == true) {
                tvSong2.post {
                    parent.getLocationOnScreen(location)
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

private fun LinearLayout.startAnim() {
    this.toVisible()
    val mCurrentPosition = floatArrayOf(0f, 0f)
    val path = Path()
    path.lineTo(-50f, -250f)
    val pathMeasure = PathMeasure(path, false)
    val valueAnimator = ValueAnimator.ofFloat(0f, pathMeasure.length)
    valueAnimator.duration = 300
    valueAnimator.interpolator = LinearInterpolator()
    valueAnimator.addUpdateListener { animation ->
        val animatedValue = animation.animatedValue as Float
        pathMeasure.getPosTan(animatedValue, mCurrentPosition, null)
        this.translationX = mCurrentPosition[0]
        this.translationY = mCurrentPosition[1]
    }
    valueAnimator.doOnEnd {
        this@startAnim.toGone()
    }
    valueAnimator.startDelay = 100
    valueAnimator.start()
}