package com.example.libnet.exception

import java.io.IOException

open class ApiException : Exception {
    var errCode: Int
    var errMsg: String?

    constructor(error: ERROR, e: Throwable? = null) : super(e) {
        errCode = error.code
        errMsg = error.errMsg
    }

    constructor(code: Int, msg: String?, e: Throwable? = null) : super(e) {
        this.errCode = code
        this.errMsg = msg
    }
}


/**
 * 无网络连接异常
 */
class NoNetWorkException(error: ERROR, e: Throwable? = null) : IOException(e) {
    var errCode: Int
    var errMsg: String

    init {
        errCode = error.code
        errMsg = error.errMsg
    }
}
