package com.pa.network.http

import com.google.gson.Gson
import com.pa.network.bean.ErrResponse
import com.pa.network.bean.ResultException
import com.pa.network.bean.ResultResponse

import java.io.IOException
import java.lang.reflect.Type

import okhttp3.ResponseBody
import retrofit2.Converter

/**
 * Created by wen on 2018/5/14.
 */

class JsonArraResponseBodyConverter<T> internal constructor(private val gson: Gson?, private val type: Type?) : Converter<ResponseBody, T> {

    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T? {
        val response = value.string()
        try {
            //ResultResponse 只解析status字段
            val resultResponse = gson?.fromJson<ResultResponse>(response, ResultResponse::class.java!!)
            if (Integer.parseInt(resultResponse?.status) == 200) {
                //result==200表示成功返回，继续用本来的Model类解析
                return gson?.fromJson<T>(response, type)
            } else {
                //ErrResponse 将msg解析为异常消息文本
                val errResponse = gson?.fromJson<ErrResponse>(response, ErrResponse::class.java!!)
                throw ResultException(resultResponse?.status, errResponse?.msg)
            }
        } finally {
        }
    }
}
