package com.pa.core.base

import com.pa.core.nethelper.RetrofitManger

import retrofit2.Retrofit

open class BaseModel : IModel {

    protected var mRetrofit: Retrofit? = null

    init {
        mRetrofit = RetrofitManger.getInstance()?.createRetrofit()
    }
}
