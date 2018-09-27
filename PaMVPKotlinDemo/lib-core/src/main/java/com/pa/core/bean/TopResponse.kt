package com.pa.core.bean

import com.google.gson.annotations.SerializedName

/**
 * Created by wen on 2018/5/14.
 */
class TopResponse<Data> {

    @SerializedName("status")
    var status: String? = null

    @SerializedName("message")
    var info: String? = null

    @SerializedName("data")
    var data: Data? = null
}
