package com.pa.core.nethelper

import android.content.Context
import android.util.Log


import com.pa.core.utils.SharedPreUtils

import java.io.IOException
import java.util.HashSet
import java.util.Locale

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Created by wen on 2018/5/14.
 */

class ReceivedCookiesInterceptor(private val mContext: Context) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        val t1 = System.nanoTime()
        val builder = request.newBuilder()
        val originalResponse = chain.proceed(chain.request())
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            val cookies = HashSet<String>()
            for (header in originalResponse.headers("Set-Cookie")) {
                cookies.add(header)
            }
            SharedPreUtils.putStringSet(mContext, Constant.SESSIONID, cookies)
        }
        return originalResponse
    }
}