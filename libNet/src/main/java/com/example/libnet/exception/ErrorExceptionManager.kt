package com.example.libnet.exception

import com.example.lib_base.ext.toast
import com.example.lib_base.manager.AppManager

/**
 * 异常处理类
 */
object ErrorExceptionManager {
    fun handlerException(e: Throwable): ApiException {
        val ex: ApiException
        when (e) {
            is ApiException -> {
                ex = ApiException(e.errCode, e.errMsg)
                "${ex.errMsg}".toast()
                when (ex.errCode) {
                    ERROR.UN_LOGIN.code, ERROR.UNAUTHORIZED.code -> {
                        AppManager.goLogin()
                    }

                    ERROR.ERROR_MONEY.code -> {
                        //TODO: 充值弹窗
                    }
                }
            }

            is NoNetWorkException -> {
                "网络异常".toast()
                ex = ApiException(e.errCode, e.errMsg)
            }

            //......HttpException等

            else -> {
                ex = if (!e.message.isNullOrEmpty()) ApiException(1000, e.message!!, e)
                else ApiException(ERROR.UNKNOWN, e)
            }
        }
        return ex
    }
}