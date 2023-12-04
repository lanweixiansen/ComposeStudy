package com.example.lib_base.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object TimeUtils {
    private const val ONE_DAY = 86400000


    fun getTime(time: Long?): String {
        time ?: return ""
        // 今天
        val todayStart = Calendar.getInstance()
        todayStart.set(Calendar.HOUR_OF_DAY, 0)
        todayStart.set(Calendar.MINUTE, 0)
        todayStart.set(Calendar.SECOND, 0)
        todayStart.set(Calendar.MILLISECOND, 0)
        val currentDate = Date(System.currentTimeMillis())
        val newsDate = Date(time)
        val calendar = Calendar.getInstance()
        // 当前年份
        val currentYear = calendar.get(Calendar.YEAR)
        calendar.time = Date(time)
        // 消息年份
        val newsYear = calendar.get(Calendar.YEAR)
        val s = when {
            // 今天
            !newsDate.before(todayStart.time) -> {
                val timeFormatter24 = SimpleDateFormat("HH:mm", Locale.getDefault())
                getAMPMDate(newsDate) + timeFormatter24.format(newsDate)
            }
            // 昨天
            !newsDate.before(Date(todayStart.time.time - ONE_DAY)) -> {
                val timeFormatter24 = SimpleDateFormat("HH:mm", Locale.getDefault())
                "昨天" + timeFormatter24.format(newsDate)
            }
            // 同一周
            isSameWeekDates(currentDate, newsDate) -> {
                val timeFormatter24 = SimpleDateFormat("HH:mm", Locale.getDefault())
                getWeekOfDate(newsDate) + timeFormatter24.format(newsDate)
            }
            // 同一年
            currentYear == newsYear -> {
                SimpleDateFormat("MM-dd HH:mm", Locale.getDefault()).format(newsDate)
            }
            // 去年
            else -> {
                SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(newsDate)
            }
        }
        return s
    }

    private fun isSameWeekDates(date1: Date, date2: Date): Boolean {
        val cal1 = Calendar.getInstance()
        val cal2 = Calendar.getInstance()
        cal1.time = date1
        cal2.time = date2
        val subYear = cal1[Calendar.YEAR] - cal2[Calendar.YEAR]
        if (0 == subYear) {
            return cal1[Calendar.WEEK_OF_YEAR] == cal2[Calendar.WEEK_OF_YEAR]
        } else if (1 == subYear && 11 == cal2[Calendar.MONTH]) {
            // 如果12月的最后一周横跨来年第一周的话则最后一周即算做来年的第一周
            return cal1[Calendar.WEEK_OF_YEAR] == cal2[Calendar.WEEK_OF_YEAR]
        } else if (-1 == subYear && 11 == cal1[Calendar.MONTH]) {
            return cal1[Calendar.WEEK_OF_YEAR] == cal2[Calendar.WEEK_OF_YEAR]
        }
        return false
    }

    private fun getWeekOfDate(date: Date): String {
        val weekDaysName =
            arrayOf("周日", "周一", "周二", "周三", "周四", "周五", "周六")
        val calendar = Calendar.getInstance()
        calendar.time = date
        val intWeek = calendar[Calendar.DAY_OF_WEEK] - 1
        return weekDaysName[intWeek]
    }

    private fun getAMPMDate(date: Date): String {
        val calendar = Calendar.getInstance()
        calendar.time = date
        val hour = calendar.get(Calendar.AM_PM)
        return if (hour == 0) "上午" else "下午"
    }
}