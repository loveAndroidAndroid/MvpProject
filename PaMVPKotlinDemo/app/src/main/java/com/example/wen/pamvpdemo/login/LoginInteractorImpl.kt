package com.example.wen.pamvpdemo.login

import com.example.wen.pamvpdemo.http.ResponseInfoAPI
import com.pa.core.base.BaseModel
import com.pa.core.bean.TopResponse

import io.reactivex.Flowable

/**
 * Created by wen on 2018/5/14.
 */
class LoginInteractorImpl : BaseModel(), LoginContract.LoginInteractor {

    //Kotlin 中 双冒号操作符 表示把一个方法当做一个参数，传递到另一个方法中进行使用，通俗的来讲就是引用一个方法。
    override fun userLogin(): Flowable<TopResponse<Any>> {
        return mRetrofit?.create(ResponseInfoAPI::class.java)!!.get()
    }
}
