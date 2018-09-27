package com.pa.network.http

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import com.pa.network.bean.ResultException

import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

import io.reactivex.subscribers.DisposableSubscriber

/**
 * Created by wen on 2018/5/14.
 * 适配器模式  去除不必要的接口方法
 */
abstract class ApiSubscriberCallBack<T> : DisposableSubscriber<T>() {

    override fun onNext(t: T) {
        onSuccess(t)
    }

    override fun onError(e: Throwable) {
        e.printStackTrace()

        //在这里做全局的错误处理
        if (e is HttpException ||
                e is ConnectException ||
                e is SocketTimeoutException ||
                e is TimeoutException ||
                e is UnknownHostException) {
            //网络错误
            onFailure(Throwable("网络不好哦亲，请确认网络重新连接"))
        } else if (e is ResultException) {
            //todo 自定义的ResultException  此处结合业务进行处理
            onFailure(e)
        } else {
            //其他错误
            onFailure(Throwable("未知错误，地球即将爆炸，请赶紧跑路"))
        }
    }

    override fun onComplete() {}

    abstract fun onSuccess(t: T)

    abstract fun onFailure(t: Throwable)
}
