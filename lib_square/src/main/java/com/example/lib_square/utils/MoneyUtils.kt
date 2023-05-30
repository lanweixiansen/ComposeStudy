package com.example.lib_square.utils

import com.example.lib_square.JobTimeActivity
import com.example.lib_square.bean.JobInfoBean
import java.math.BigDecimal
import java.math.RoundingMode

object MoneyUtils {
    private lateinit var bean: JobInfoBean

    fun init(bean: JobInfoBean) {
        this.bean = bean
    }

    /**
     * 计算当天已获得money
     */
    fun getHaveObtainedMoney(presentTime: Long): Float {
        val money = getMillisecondMoney()
        return money * presentTime / 1000
    }

    /**
     * 计算平均每秒获得money
     */
    fun getMillisecondMoney(): Float {
        val dayMoney = bean.money / JobTimeActivity.WORKING_DAY
        val workTime: Long = ((bean.getEndTimer().toLong() - bean.startTime.toLong()) * 3600)
        return BigDecimal(dayMoney.toDouble()).divide(BigDecimal(workTime), 4, RoundingMode.HALF_DOWN)
            .toFloat()
    }
}