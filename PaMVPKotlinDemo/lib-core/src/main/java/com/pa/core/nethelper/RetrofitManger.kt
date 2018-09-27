package com.pa.core.nethelper


import com.pa.core.config.NetworkConfig
import com.pa.network.http.RetrofitUtil

import retrofit2.Retrofit

/**
 * Created by zhangxiaowen on 2018/8/7.Context context
 */
class RetrofitManger {

    companion object {

        @Volatile internal var instance: RetrofitManger? = null

        /**
         * 静态内部类
         */
        fun getInstance(): RetrofitManger? {
            return instance ?: synchronized(this) {
                instance ?: RetrofitManger().also { instance = it }
            }
        }
    }

    fun init(mNetworkConfig: NetworkConfig) {
        RetrofitUtil.getInstance()?.init(mNetworkConfig.baseURL, mNetworkConfig.readTime, mNetworkConfig.writeTime, mNetworkConfig.connectTime, null, null, mNetworkConfig.interceptors)
    }

    fun createRetrofit(): Retrofit? {
        return RetrofitUtil.getInstance()?.retrofit
    }
}
