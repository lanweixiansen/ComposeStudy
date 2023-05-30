package com.example.lib_square.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.LinkedList

object TimeUtils {

    /**
     * 获取当前已消耗时间
     */
    fun getPresentTime(startTime: Float): Long {
        val date = Date(System.currentTimeMillis())
        val timeArray = LinkedList<Long>()
        SimpleDateFormat("HH mm ss").format(date).forEach {
            if (it.toString().isNotBlank()) {
                timeArray.add(it.toString().toLong())
            }
        }
        // 计算当前毫秒数
        val currentTime =
            (timeArray[0] * 10 + timeArray[1]) * 3600000 + (timeArray[2] * 10 + timeArray[3]) * 60000 + (timeArray[4] * 10 + timeArray[5]) * 1000
        return (currentTime - startTime * 3600000).toLong()
    }
}