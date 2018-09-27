package com.pa.network.http

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

import java.util.concurrent.TimeUnit

import javax.net.SocketFactory
import javax.net.ssl.HostnameVerifier

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
 * ResponseInfoAPI工具类
 */
class RetrofitUtil{

    /**
     * 得到Retrofit的对象
     *
     * @return
     */
    public var retrofit: Retrofit? = null

    private var gson: Gson? = null

    companion object {

        @Volatile internal var instance: RetrofitUtil? = null

        /**
         * 静态内部类
         */
        fun getInstance(): RetrofitUtil?{
            return instance ?: synchronized(this) {
                instance ?: RetrofitUtil().also { instance = it }
            }
        }
    }

    fun init(baseURL: String?, readTime: Long, writeTime: Long, connectTime: Long, socketFactory: SocketFactory?, hostnameVerifier: HostnameVerifier?, interceptor: Array<Interceptor>?) {
        if (gson == null) {
            gson = GsonBuilder()
                    .enableComplexMapKeySerialization() //支持Map的key为复杂对象的形式
                    .create()
        }
        retrofit = Retrofit.Builder()
                .baseUrl(baseURL)
                .client(genericClient(readTime, writeTime, connectTime, socketFactory, hostnameVerifier, interceptor))
                //2.自定义ConverterFactory处理异常情况
                .addConverterFactory(JsonArrayConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    fun genericClient(readTime: Long, writeTime: Long, connectTime: Long, socketFactory: SocketFactory?, hostnameVerifier: HostnameVerifier?, interceptors: Array<Interceptor>?): OkHttpClient {
        val builder = OkHttpClient.Builder()
                .readTimeout(readTime, TimeUnit.SECONDS)
                .writeTimeout(writeTime, TimeUnit.SECONDS)
                .connectTimeout(connectTime, TimeUnit.SECONDS)
        //      可以添加进来https认证
        //              .socketFactory(socketFactory)
        //              .hostnameVerifier(hostnameVerifier);
        if (interceptors != null && interceptors.size > 0) {
            for (i in interceptors.indices) {
                builder.addInterceptor(interceptors[i])
            }
        }
        return builder.build()
    }
}
