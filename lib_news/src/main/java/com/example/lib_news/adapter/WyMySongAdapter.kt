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
    var isAnimItem: Boolean = false
)

private val location = IntArray(2)

// 我的歌单Adapter
class MySongAdapter : BaseSingleItemAdapter<WySongEntry, MySongAdapter.VH>() {
    private var mOnClick: ((MySongListBean, Int, LinearLayout) -> Unit)? = null

    class VH(val viewBinding: NewsRvItemSongTitleBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    override fun onBindViewHolder(holder: VH, item: WySongEntry?) {
        with(holder.viewBinding) {
            tvTitle.text = item?.title
            rvSongs.layoutManager = GridLayoutManager(context, 4)
            rvSongs.adapter = WySongAdapter(true).also {
                it.submitList(item?.songList)
                it.setOnClickListener { bean, view ->
                    mOnClick?.invoke(bean, holder.layoutPosition, view)
                }
            }
        }
    }

    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): VH {
        return VH(NewsRvItemSongTitleBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    fun setOnClickListener(onClick: (MySongListBean, Int, LinearLayout) -> Unit) {
        mOnClick = onClick
    }
}

//所有歌单Adapter
class SongAdapter : BaseQuickAdapter<WySongEntry, SongAdapter.VH>() {
    private var mOnClick: ((MySongListBean, Int, LinearLayout) -> Unit)? = null

    class VH(val viewBinding: NewsRvItemSongTitleBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    override fun onBindViewHolder(holder: VH, position: Int, item: WySongEntry?) {
        with(holder.viewBinding) {
            tvTitle.text = item?.title
            rvSongs.layoutManager = GridLayoutManager(context, 4)
            rvSongs.adapter = WySongAdapter(false).also {
                it.submitList(item?.songList)
                it.setOnClickListener { bean, view ->
                    mOnClick?.invoke(bean, position, view)
                }
            }
        }
    }

    override fun onCreateViewHolder(context: Context, parent: ViewGroup, viewType: Int): VH {
        return VH(NewsRvItemSongTitleBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    fun setOnClickListener(onClick: (MySongListBean, Int, LinearLayout) -> Unit) {
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
                if (item != null) {
                    mOnClick?.invoke(item, animLayout)
                    item.canClick = false
                    parent.isEnabled = false
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

fun LinearLayout.startAnim(size: Int) {
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
//    val mCurrentPosition = floatArrayOf(0f, 0f)
//    val path = Path()
//    path.lineTo(
//        (location[0] - oldLocation[0]).toFloat(),
//        (location[1] - oldLocation[1]).toFloat()
//    )
//    val pathMeasure = PathMeasure(path, false)
//    val valueAnimator = ValueAnimator.ofFloat(0f, pathMeasure.length)
//    valueAnimator.duration = 500
//    valueAnimator.interpolator = LinearInterpolator()
//    valueAnimator.addUpdateListener { animation ->
//        val animatedValue = animation.animatedValue as Float
//        pathMeasure.getPosTan(animatedValue, mCurrentPosition, null)
//        this.translationX = mCurrentPosition[0]
//        this.translationY = mCurrentPosition[1]
//    }
//    valueAnimator.doOnEnd {
//        this.postDelayed({
//            this.translationX = 0f
//            this.translationY = 0f
//            this@startAnim.toInvisible()
//        }, 200)
//    }
//    valueAnimator.start()
    val animHolderX = PropertyValuesHolder.ofFloat(
        "translationX",
        location[0].toFloat() - oldLocation[0].toFloat()
    )
    val animHolderY = PropertyValuesHolder.ofFloat(
        "translationY",
        location[1].toFloat() - oldLocation[1].toFloat()
    )
    val anim = ObjectAnimator.ofPropertyValuesHolder(this, animHolderX, animHolderY)
    anim.duration = 500
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

var rvTranslationY = 0