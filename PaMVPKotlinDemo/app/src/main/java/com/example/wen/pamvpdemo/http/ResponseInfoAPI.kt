package com.example.wen.pamvpdemo.http


import com.pa.core.bean.TopResponse
import io.reactivex.Flowable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * retrofit定义接口
 * 写URL的地方
 */
interface ResponseInfoAPI {

    //登录
    @GET(ApiUrls.test)
    fun get(): Flowable<TopResponse<Any>>


    //登录
    @POST(ApiUrls.test)
    @FormUrlEncoded
    fun getPsot(@Field("username") username: String, @Field("password") password: String): Flowable<TopResponse<Object>>
}
