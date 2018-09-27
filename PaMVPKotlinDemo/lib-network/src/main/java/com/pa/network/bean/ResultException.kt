package com.pa.network.bean

/**
 * Created by wen on 2018/5/14.
 */
class ResultException(errCode: String?, msg: String?) : RuntimeException(msg) {

    var errCode : String? = ""

    init {
        this.errCode = errCode
    }
}
