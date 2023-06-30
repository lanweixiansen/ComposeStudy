package com.example.lib_base.manager

import com.tencent.mmkv.MMKV

/**
 * MMKV 实现本地简单数据存储
 */
object AppData {
    private val mmkv = MMKV.defaultMMKV()
    private const val PRIVACY_AGREE = "privacy_agree"


    fun isAgreePrivacy() = mmkv.getBoolean(PRIVACY_AGREE, false)


    fun agreePrivacy() {
        mmkv.encode(PRIVACY_AGREE, true)
    }
}