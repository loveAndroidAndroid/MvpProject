package com.pa.core.base;

import com.pa.core.bean.TopResponse;
import com.pa.network.http.ApiSubscriberCallBack;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Created by wen on 2018/5/14.
 */
public class BasePresenter<M extends IModel, V extends IView> implements IPresenter {

    //可以快速解除所有添加的Disposable类
    private static final CompositeDisposable disposables = new CompositeDisposable();

    protected M model;
    protected V view;

    public BasePresenter(M model, V view) {
        this.model = model;
        this.view = view;
    }

    /**
     * @param flowable Flowable默认队列大小为128
     *                 背压是一种策略，具体措施是下游观察者通知上游的被观察者发送事件
     *                 背压策略很好的解决了异步环境下被观察者和观察者速度不一致的问题
     */
    public <T> void subscribe(Flowable<TopResponse<T>> flowable, ApiSubscriberCallBack<T> apiSubscriberCallBack) {

        DisposableSubscriber sub = flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onBackpressureBuffer()
                .flatMap(new Function<TopResponse<T>, Publisher<T>>() {
                    @Override
                    public Publisher<T> apply(TopResponse<T> tTopResponse) throws Exception {
                        if (tTopResponse.getStatus().equals("200")) {
                            return Flowable.just(tTopResponse.getData());
                        } else {
                            return Flowable.error(new Throwable(tTopResponse.getInfo()));
                        }
                    }
                })
                .subscribeWith(apiSubscriberCallBack);
        registerDispose(sub);
    }

    protected Disposable registerDispose(Disposable disposable) {
        if (disposables.add(disposable)) {
            return disposable;
        }
        return null;
    }

    /**
     * 解除所有的绑定
     */
    @Override
    public void onDestroy() {
        unRegisterDispose();
        if (null != this.model) {
            this.model = null;
        }
        if (null != this.view) {
            this.view = null;
        }
    }

    @Override
    public void unRegisterDispose() {
        if (null != disposables) {
            disposables.clear();
        }
    }
}
