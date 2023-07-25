package com.example.lib_base.manager

import com.tencent.mmkv.MMKV

/**
 * MMKV 实现本地简单数据存储
 */
object AppData {
    private val mmkv = MMKV.defaultMMKV()
    private const val PRIVACY_AGREE = "privacy_agree"
    private const val USER_TOKEN = "user_token"

    fun isAgreePrivacy() = mmkv.getBoolean(PRIVACY_AGREE, false)

    fun agreePrivacy() {
        mmkv.encode(PRIVACY_AGREE, true)
    }

    fun getToken() = mmkv.getString(USER_TOKEN, "")

    fun saveToken(token: String?) {
        mmkv.encode(USER_TOKEN, token)
    }

    fun isLogin() = !getToken().isNullOrBlank()
}