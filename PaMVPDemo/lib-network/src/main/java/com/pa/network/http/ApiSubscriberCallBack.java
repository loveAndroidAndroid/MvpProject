package com.pa.network.http;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.pa.network.bean.ResultException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Created by wen on 2018/5/14.
 * 适配器模式  去除不必要的接口方法
 */
public abstract class ApiSubscriberCallBack<T> extends DisposableSubscriber<T> {

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();

        //在这里做全局的错误处理
        if (e instanceof HttpException||
                e instanceof ConnectException ||
                e instanceof SocketTimeoutException ||
                e instanceof TimeoutException ||
                e instanceof UnknownHostException) {
            //网络错误
            onFailure(new Throwable("网络不好哦亲，请确认网络重新连接"));
        } else if (e instanceof ResultException) {
            //todo 自定义的ResultException  此处结合业务进行处理
            onFailure(e);
        } else {
            //其他错误
            onFailure(new Throwable("未知错误，地球即将爆炸，请赶紧跑路"));
        }
    }

    @Override
    public void onComplete() {
    }

    public abstract void onSuccess(T t);

    public abstract void onFailure(Throwable t);
}
