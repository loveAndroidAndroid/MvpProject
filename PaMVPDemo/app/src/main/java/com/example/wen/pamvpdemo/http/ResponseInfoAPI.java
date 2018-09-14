package com.example.wen.pamvpdemo.http;


import com.pa.core.bean.TopResponse;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * retrofit定义接口
 * 写URL的地方
 */

public interface ResponseInfoAPI {

    //登录
    @GET(ApiUrls.test)
    Flowable<TopResponse<Object>> getObject();


    //登录
//    @POST(ApiUrls.test)
//    @FormUrlEncoded
//    Flowable<TopResponse<Object>> getPsot(@Field("username") String username, @Field("password") String password);

}
