package com.example.lib_news.activity

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lib_base.BaseActivity
import com.example.lib_base.utils.RouteConsts
import com.example.lib_news.R
import com.example.lib_news.adapter.MySongListBean
import com.example.lib_news.adapter.SongAdapter
import com.example.lib_news.adapter.WySongAdapter
import com.example.lib_news.adapter.WySongEntry
import com.example.lib_news.databinding.NewsActivityWxManagerBinding
import com.therouter.router.Route

@Route(path = RouteConsts.NEWS_ROUTE_WY_MANAGER_ACTIVITY)
class WyManagerActivity : BaseActivity<NewsActivityWxManagerBinding>() {
    private val mMyAdapter = SongAdapter(true)
    private val mAllAdapter = SongAdapter(false)

    override fun initView() {
        with(mBinding) {
            rvMySong.adapter = mMyAdapter
            rvMySong.layoutManager = LinearLayoutManager(this@WyManagerActivity)
            rvAllSong.layoutManager = LinearLayoutManager(this@WyManagerActivity)
            rvAllSong.adapter = mAllAdapter
            rvAllSong.setHasFixedSize(true)
        }
    }

    override fun initDate() {
        mMyAdapter.submitList(mMySongList)
        mAllAdapter.submitList(mSongList)
    }

    override fun initListener() {
        super.initListener()



    }


    private val mMySongList = listOf(
        WySongEntry(
            "我的歌单",
            listOf(
                MySongListBean("电子", false),
                MySongListBean("国风", false),
                MySongListBean("摇滚", false),
                MySongListBean("爱上大时代", true),
                MySongListBean("asd", true),
                MySongListBean("弃我而去", true),
                MySongListBean("奥术大", true),
                MySongListBean("地方", true),
                MySongListBean("同意", true)
            )
        )
    )

    private val mSongList = listOf(
        WySongEntry(
            "语种",
            listOf(
                MySongListBean("查询", false),
                MySongListBean("自选", false),
                MySongListBean("秩序部", false),
                MySongListBean("请问", true),
                MySongListBean("额外", true),
                MySongListBean("热天", true),
                MySongListBean("一天", true),
                MySongListBean("儿童", true),
                MySongListBean("原图", true)
            )
        ),
        WySongEntry(
            "风格",
            listOf(
                MySongListBean("欧阳", false),
                MySongListBean("乳突炎", false),
                MySongListBean("任天宇", false),
                MySongListBean("有人讨厌", true),
                MySongListBean("如同一", true),
                MySongListBean("二天", true),
                MySongListBean("古典风格", true),
                MySongListBean("冻干粉的", true),
                MySongListBean("电饭锅", true)
            )
        ),
        WySongEntry(
            "情感",
            listOf(
                MySongListBean("回归", false),
                MySongListBean("根据", false),
                MySongListBean("感觉", false),
                MySongListBean("估计", true),
                MySongListBean("电饭锅和", true),
                MySongListBean("深度覆盖", true),
                MySongListBean("构建", true),
                MySongListBean("离开", true),
                MySongListBean("那么", true)
            )
        ),
        WySongEntry(
            "主题",
            listOf(
                MySongListBean("你们", false),
                MySongListBean("匿名", false),
                MySongListBean("摇滚", false),
                MySongListBean("xvv", true),
                MySongListBean("123", true),
                MySongListBean("少的发", true),
                MySongListBean("松岛枫", true),
                MySongListBean("水电费", true),
                MySongListBean("斯蒂芬", true)
            )
        ),
    )
}