package com.pa.core.base;

import com.pa.core.nethelper.RetrofitManger;

import retrofit2.Retrofit;

public class BaseModel implements IModel {

    protected Retrofit mRetrofit;

    public BaseModel(){
        mRetrofit = RetrofitManger.getInstance().createRetrofit();
    }
}
