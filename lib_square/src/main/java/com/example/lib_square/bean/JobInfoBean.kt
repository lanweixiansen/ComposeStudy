package com.example.lib_square.bean

import java.io.Serializable

data class JobInfoBean(
    val money: Int, val startTime: Float, val endTime: Float, val releaseTime: Float = 0f
) : Serializable{
    fun getEndTimer(): Float = if (endTime < 12f) endTime + 12f else endTime
}