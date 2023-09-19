package com.example.lib_news.adapter

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.LinearLayout
import androidx.core.animation.doOnEnd
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseSingleItemAdapter
import com.example.lib_news.activity.mRvTranslationY
import com.example.lib_news.databinding.NewsRvItemSongBinding
import com.example.lib_news.databinding.NewsRvItemSongTitleBinding
import com.example.uilibrary.uiUtils.dp2px
import com.example.uilibrary.uiUtils.getScreenWidth
import com.example.uilibrary.uiUtils.toInvisible
import com.example.uilibrary.uiUtils.toVisible

data class WySongEntry(
    val title: String?,
    val songList: List<MySongListBean>?
)

data class MySongListBean(
    val name: String?,
    var canClick: Boolean,
    var isAnimItem: Boolean = false,
    var x: Int = 0,
    var y: Int = 0
)

// 我的歌单Adapter
class MySongAdapter : BaseSingleItemAdapter<WySongEntry, MySongAdapter.VH>() {
    private var mOnClick: ((MySongListBean, LinearLayout) -> Unit)? = null
    private lateinit var mAdapter: WySongAdapter

    class VH(val viewBinding: NewsRvItemSongTitleBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    override fun onBindViewHolder(holder: VH, item: WySongEntry?) {
        with(holder.viewBinding) {
            tvTitle.text = item?.title
            rvSongs.layoutManager = GridLayoutManager(context, 4)
            rvSongs.adapter = WySongAdapter(true).also {
                mAdapter = it
                it.submitList(item?.songList)
                it.setOnClickListener { bean, view ->
                    mOnClick?.invoke(bean, view)
                }
            }
        }
    }

    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): VH {
        return VH(NewsRvItemSongTitleBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    fun setOnClickListener(onClick: (MySongListBean, LinearLayout) -> Unit) {
        mOnClick = onClick
    }

    fun addItemDate(bean: MySongListBean) {
        mAdapter.add(bean)
        notifyItemChanged(0, "sample_pay_load")
    }

    fun removeItemData(bean: MySongListBean) {
        mAdapter.remove(bean)
        notifyItemChanged(0, "sample_pay_load")
    }
}

//所有歌单Adapter
class SongAdapter : BaseQuickAdapter<WySongEntry, SongAdapter.VH>() {
    private var mOnClick: ((MySongListBean, LinearLayout) -> Unit)? = null

    class VH(val viewBinding: NewsRvItemSongTitleBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    override fun onBindViewHolder(holder: VH, position: Int, item: WySongEntry?) {
        with(holder.viewBinding) {
            tvTitle.text = item?.title
            rvSongs.layoutManager = GridLayoutManager(context, 4)
            rvSongs.adapter = WySongAdapter(false).also {
                it.submitList(item?.songList)
                it.setOnClickListener { bean, view ->
                    mOnClick?.invoke(bean, view)
                }
            }
        }
    }

    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): VH {
        return VH(NewsRvItemSongTitleBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    fun setOnClickListener(onClick: (MySongListBean, LinearLayout) -> Unit) {
        mOnClick = onClick
    }
}

// 歌曲Adapter
class WySongAdapter(private val isMySong: Boolean) :
    BaseQuickAdapter<MySongListBean, WySongAdapter.SongVH>() {
    private var mOnClick: ((MySongListBean, LinearLayout) -> Unit)? = null

    class SongVH(val viewBinding: NewsRvItemSongBinding) : RecyclerView.ViewHolder(viewBinding.root)

    override fun onBindViewHolder(holder: SongVH, position: Int, item: MySongListBean?) {
        with(holder.viewBinding) {
            tvSong.text = item?.name
            tvSong2.text = item?.name
            btn.isSelected = isMySong
            parent.isEnabled = item?.canClick ?: false
            parent.setOnClickListener {
                if (isMySong) parent.toInvisible()
                if (item != null) {
                    mOnClick?.invoke(item, animLayout)
                    item.canClick = false
                    parent.isEnabled = false
                    val location = IntArray(2)
                    parent.getLocationOnScreen(location)
                    item.x = location[0]
                    item.y = location[1] + mRvTranslationY
                }
            }
            if (item?.isAnimItem == true) {
                parent.toInvisible()
                parent.postDelayed({
                    parent.toVisible()
                    item.isAnimItem = false
                }, 500)
            }
        }
    }

    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): SongVH {
        return SongVH(NewsRvItemSongBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    fun setOnClickListener(onClick: (MySongListBean, LinearLayout) -> Unit) {
        mOnClick = onClick
    }
}

fun LinearLayout.startAnim(size: Int, rvTranslationY: Int) {
    val location = IntArray(2)
    this.elevation = dp2px(10f)
    // 根据size计算新添加的item的位置
    // 每个item的宽度
    val itemWidth = (getScreenWidth() - dp2px(24)) / 4
    // 根据item宽度计算出新增的item的x坐标
    location[0] = ((dp2px(12) + ((size % 4)) * itemWidth).toInt())
    // 计算新增item的y坐标(状态栏高度 + 标题栏高度 + adapter标题高度 + item高度)
    val lineNum = (size + 1) / 4 + if ((size + 1) % 4 <= 1) 0 else 1
    location[1] = dp2px(77) + dp2px(44) + (lineNum - 1) * dp2px(40) - rvTranslationY
    val oldLocation = IntArray(2)
    this.getLocationOnScreen(oldLocation)
    this.toVisible()
    val animHolderX = PropertyValuesHolder.ofFloat(
        "translationX",
        location[0].toFloat() - oldLocation[0].toFloat()
    )
    val animHolderY = PropertyValuesHolder.ofFloat(
        "translationY",
        location[1].toFloat() - oldLocation[1].toFloat()
    )
    val anim = ObjectAnimator.ofPropertyValuesHolder(this, animHolderX, animHolderY)
    anim.duration = 400
    anim.interpolator = LinearInterpolator()
    anim.start()
    anim.doOnEnd {
        this.postDelayed({
            this.translationX = 0f
            this.translationY = 0f
            this@startAnim.toInvisible()
        }, 300)
    }
}

fun LinearLayout.endAnim(size: Int, rvTranslationY: Int, x: Int, y: Int) {
    // 行数变化计算
    val scrollY = if ((size-1) % 4 == 0) dp2px(40f) else 0f
    this.elevation = dp2px(10f)
    val oldLocation = IntArray(2)
    this.getLocationOnScreen(oldLocation)
    this.toVisible()
    val animHolderX = PropertyValuesHolder.ofFloat(
        "translationX",
        (x.toFloat() - oldLocation[0].toFloat() - dp2px(4f))
    )
    val animHolderY = PropertyValuesHolder.ofFloat(
        "translationY",
        (y.toFloat() - oldLocation[1].toFloat() - dp2px(4f) + scrollY - rvTranslationY)
    )
    val anim = ObjectAnimator.ofPropertyValuesHolder(this, animHolderX, animHolderY)
    anim.duration = 400
    anim.interpolator = LinearInterpolator()
    anim.start()
    anim.doOnEnd {
        this.postDelayed({
            this.translationX = 0f
            this.translationY = 0f
            this.toInvisible()
        }, 300)
    }
}