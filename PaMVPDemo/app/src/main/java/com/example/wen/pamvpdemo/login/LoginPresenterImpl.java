package com.example.wen.pamvpdemo.login;

import com.pa.core.base.BasePresenter;
import com.pa.core.bean.TopResponse;
import com.pa.network.http.ApiSubscriberCallBack;

import io.reactivex.Flowable;

/**
 * Created by wen on 2018/5/14.
 */
public class LoginPresenterImpl extends BasePresenter<LoginContract.LoginInteractor, LoginContract.LoginView>
        implements LoginContract.LoginPresenter {


    private LoginContract.LoginInteractor loginInteractor;
    private LoginContract.LoginView loginView;
    private Flowable<TopResponse<Object>> userLogin;

    public LoginPresenterImpl(LoginContract.LoginView loginView,
                              LoginContract.LoginInteractor loginInteractor) {
        super(loginInteractor, loginView);
        this.loginInteractor = loginInteractor;
        this.loginView = loginView;
    }

    @Override
    public void login() {
        view.showLoadingView();
        userLogin = loginInteractor.getUserLogin();
        ApiSubscriberCallBack<Object> apiSubscriberCallBack = new ApiSubscriberCallBack<Object>() {

            @Override
            public void onSuccess(Object object) {
                loginView.onSuccess(object);
                view.hideLoadingView();
            }

            @Override
            public void onFailure(Throwable t) {
                loginView.onFailure();
                view.hideLoadingView();
            }
        };
        subscribe(userLogin,apiSubscriberCallBack);
    }
}
