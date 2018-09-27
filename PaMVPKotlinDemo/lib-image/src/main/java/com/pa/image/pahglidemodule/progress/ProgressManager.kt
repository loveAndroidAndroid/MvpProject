package com.pa.image.pahglidemodule.progress

import android.text.TextUtils
import com.pa.image.pahglidemodule.progress.ProgressManager.listenersMap

import java.io.IOException
import java.util.Collections
import java.util.HashMap

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

/**
 * 进度管理者
 */
object ProgressManager {

    //得到线程安全的hashmap
    private val listenersMap = Collections.synchronizedMap(HashMap<String, OnProgressListener>())
    private var okHttpClient: OkHttpClient? = null

    private val LISTENER = object : ProgressResponseBody.InternalProgressListener {
        override fun onProgress(url: String, bytesRead: Long, totalBytes: Long) {
            val onProgressListener = getProgressListener(url)
            if (onProgressListener != null) {
                val percentage = (bytesRead * 1f / totalBytes * 100f).toInt()
                val isComplete = percentage >= 100
                onProgressListener.onProgress(isComplete, percentage, bytesRead, totalBytes)
                if (isComplete) {
                    removeListener(url)
                }
            }
        }
    }

    //得到OkHttpClient对象
    fun getOkHttpClient(): OkHttpClient? {
        if (okHttpClient == null) {
            okHttpClient = OkHttpClient.Builder()
                    .addNetworkInterceptor { chain ->
                        val request = chain.request()
                        val response = chain.proceed(request)
                        response.newBuilder()
                                .body(ProgressResponseBody(request.url().toString(), LISTENER, response.body()!!))
                                .build()
                    }
                    .build()
        }
        return okHttpClient
    }

    fun addListener(url: String, listener: OnProgressListener?) {
        if (!TextUtils.isEmpty(url) && listener != null) {
            listenersMap!!.put(url, listener)
            listener.onProgress(false, 1, 0, 0)
        }
    }

    fun removeListener(url: String) {
        if (!TextUtils.isEmpty(url)) {
            listenersMap!!.remove(url)
        }
    }

    fun getProgressListener(url: String): OnProgressListener? {
        return if (TextUtils.isEmpty(url) || listenersMap == null || listenersMap.size == 0) {
            null
        } else listenersMap[url]

    }
}
