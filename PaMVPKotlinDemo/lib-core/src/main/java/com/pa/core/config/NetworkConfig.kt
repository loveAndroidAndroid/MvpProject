package com.pa.core.config

import okhttp3.Interceptor

class NetworkConfig private constructor(builder: Builder) {

    val baseURL: String?
    val readTime: Long
    val writeTime: Long
    val connectTime: Long
    val interceptors: Array<Interceptor>?

    //新的关键字init用来处理类的初始化问题，init模块中的内容可以直接使用构造函数的参数
    init {
        this.baseURL = builder.baseURL
        this.readTime = builder.readTime
        this.writeTime = builder.writeTime
        this.connectTime = builder.connectTime
        this.interceptors = builder.interceptors
    }

    class Builder {

        //lateinit只能在不可null的对象上使用，比须为var，不能为primitives（Int、Float之类）
        var baseURL: String? = null
        var readTime: Long = 30
        var writeTime: Long = 30
        var connectTime: Long = 30
        var interceptors: Array<Interceptor>? = null


        fun setBaseURL(baseURL: String): Builder {
            this.baseURL = baseURL
            return this
        }

        fun setReadTime(readTime: Long): Builder {
            this.readTime = readTime
            return this
        }

        fun setWriteTime(writeTime: Long): Builder {
            this.writeTime = writeTime
            return this
        }

        fun setConnectTime(connectTime: Long): Builder {
            this.connectTime = connectTime
            return this
        }

        //vararg：可变参数
        fun setInterceptor(interceptors: Array<Interceptor>?): Builder {
            this.interceptors = interceptors
            return this
        }

        fun build(): NetworkConfig {
            return NetworkConfig(this)
        }
    }
}
