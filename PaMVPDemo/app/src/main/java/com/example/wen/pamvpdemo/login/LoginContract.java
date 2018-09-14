package com.example.wen.pamvpdemo.login;

import com.pa.core.base.IModel;
import com.pa.core.base.IPresenter;
import com.pa.core.base.IView;
import com.pa.core.bean.TopResponse;

import io.reactivex.Flowable;

/**
 * Created by wen on 2018/5/14.
 */
public interface LoginContract {

    interface LoginView extends IView {

        void onSuccess(Object object);

        void onFailure();

    }

    interface LoginInteractor extends IModel {
        Flowable<TopResponse<Object>> getUserLogin();
    }

    interface LoginPresenter extends IPresenter {

        void login();
    }
}
