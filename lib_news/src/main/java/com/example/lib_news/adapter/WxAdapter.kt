package com.example.lib_news.adapter

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Path
import android.graphics.PathMeasure
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import androidx.core.animation.doOnEnd
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseMultiItemAdapter
import com.example.lib_news.data.WxEntity
import com.example.lib_news.databinding.NewsRvItemOtherTextMessageBinding
import com.example.lib_news.databinding.NewsRvItemOwnerEmojiMessageBinding
import com.example.uilibrary.uiUtils.dp2px
import com.example.uilibrary.uiUtils.getScreenWidth

class WxAdapter : BaseMultiItemAdapter<WxEntity>() {
    private var mOnAdd: (() -> Unit)? = null
    private var mOnAnimEnd: (() -> Unit)? = null

    companion object {
        const val OTHER_EMOJI_MESSAGE = 0
        const val OWNER_EMOJI_MESSAGE = 1
    }

    class OtherTextMessageVH(val viewBinding: NewsRvItemOtherTextMessageBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    class OwnerTextMessageVH(val viewBinding: NewsRvItemOwnerEmojiMessageBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    init {
        addItemType(
            OTHER_EMOJI_MESSAGE,
            object : OnMultiItemAdapterListener<WxEntity, OtherTextMessageVH> {
                override fun onBind(holder: OtherTextMessageVH, position: Int, item: WxEntity?) {

                }

                override fun onCreate(context: Context, parent: ViewGroup, viewType: Int) =
                    OtherTextMessageVH(
                        NewsRvItemOtherTextMessageBinding.inflate(
                            LayoutInflater.from(context), parent, false
                        )
                    )
            })
        addItemType(
            OWNER_EMOJI_MESSAGE,
            object : OnMultiItemAdapterListener<WxEntity, OwnerTextMessageVH> {
                override fun onBind(holder: OwnerTextMessageVH, position: Int, item: WxEntity?) {
                    if (item?.showAnim == true) {
                        item.showAnim = false
                        holder.viewBinding.ivEmoji.showBaoAnim()
                    }
                }

                override fun onCreate(context: Context, parent: ViewGroup, viewType: Int) =
                    OwnerTextMessageVH(
                        NewsRvItemOwnerEmojiMessageBinding.inflate(
                            LayoutInflater.from(context), parent, false
                        )
                    )
            })
        onItemViewType { position, list ->
            when (list[position].newsType) {
                0 -> OTHER_EMOJI_MESSAGE
                1 -> OWNER_EMOJI_MESSAGE
                else -> OWNER_EMOJI_MESSAGE
            }
        }
    }

    override fun add(data: WxEntity) {
        super.add(data)
        mOnAdd?.invoke()
    }


    fun setOnAddDataListener(onAdd: () -> Unit) {
        mOnAdd = onAdd
    }

    fun setOnAnimEndListener(onAnimEnd: () -> Unit) {
        mOnAnimEnd = onAnimEnd
    }

    private fun ImageView.showBaoAnim() {
        if (!(getItemViewType(items.size - 2) == OTHER_EMOJI_MESSAGE && getItemViewType(items.size - 1) == OWNER_EMOJI_MESSAGE)) {
            return
        }

        val endX = getScreenWidth() - dp2px(162f)
        val endY = dp2px(54f)
        val mCurrentPosition = floatArrayOf(0f, 0f)
        val path = Path()
        path.quadTo(-(getScreenWidth() / 2f), -(endY + dp2px(90f)), -endX, -endY)
        val pathMeasure = PathMeasure(path, false)
        val valueAnimator = ValueAnimator.ofFloat(0f, pathMeasure.length)
        valueAnimator.duration = 1000
        valueAnimator.interpolator = LinearInterpolator()
        valueAnimator.addUpdateListener { animation ->
            val animatedValue = animation.animatedValue as Float
            pathMeasure.getPosTan(animatedValue, mCurrentPosition, null)
            this.translationX = mCurrentPosition[0]
            this.translationY = mCurrentPosition[1]
            this.rotation = animatedValue
        }
        valueAnimator.doOnEnd {
            mOnAnimEnd?.invoke()
            this.translationX = 0f
            this.translationY = 0f
            this.rotation = 0f
            valueAnimator.removeAllUpdateListeners()
        }
        valueAnimator.start()
    }
}


