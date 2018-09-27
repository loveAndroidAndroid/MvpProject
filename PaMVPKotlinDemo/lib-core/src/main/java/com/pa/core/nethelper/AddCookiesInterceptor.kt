package com.pa.core.nethelper

import android.content.Context

import com.pa.core.utils.SharedPreUtils

import java.io.IOException
import java.util.HashSet

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Created by wen on 2018/5/14.
 */
class AddCookiesInterceptor(private val mContext: Context) : Interceptor {

    //Kotlin 中 双冒号操作符 表示把一个方法当做一个参数，传递到另一个方法中进行使用，通俗的来讲就是引用一个方法。
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val builder = chain.request().newBuilder()
        val stringSet = SharedPreUtils.getStringSet(mContext, Constant.SESSIONID, HashSet())
        for (cookie in stringSet!!) {
            builder.addHeader("Cookie", cookie)
        }
        return chain.proceed(builder.build())
    }
}
