package com.pa.core.base

import com.pa.core.bean.TopResponse
import com.pa.network.http.ApiSubscriberCallBack

import org.reactivestreams.Publisher

import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber

/**
 * Created by wen on 2018/5/14.
 */
open class BasePresenter<M : IModel, V : IView>(protected var model: M?, protected var view: V?) : IPresenter {

    companion object {

        //可以快速解除所有添加的Disposable类
        private val disposables = CompositeDisposable()
    }

    /**
     * @param flowable Flowable默认队列大小为128
     * 背压是一种策略，具体措施是下游观察者通知上游的被观察者发送事件
     * 背压策略很好的解决了异步环境下被观察者和观察者速度不一致的问题
     */
    fun <T> subscribe(flowable: Flowable<TopResponse<T>>, apiSubscriberCallBack: ApiSubscriberCallBack<T>) {

        val sub = flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onBackpressureBuffer()
                .flatMap { tTopResponse ->
                    if (tTopResponse.status == "200") {
                        Flowable.just(tTopResponse.data)
                    } else {
                        Flowable.error(Throwable(tTopResponse.info))
                    }
                }
                .subscribeWith(apiSubscriberCallBack)
        registerDispose(sub)
    }

    protected fun registerDispose(disposable: Disposable): Disposable? {
        return if (disposables.add(disposable)) {disposable} else null
    }

    /**
     * 解除所有的绑定
     */
    override fun onDestroy() {
        unRegisterDispose()
        if (null != this.model) {
            this.model = null
        }
        if (null != this.view) {
            this.view = null
        }
    }

    override fun unRegisterDispose() {
        disposables.clear()
    }
}
