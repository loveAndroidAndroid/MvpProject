package com.example.wen.pamvpdemo.login;

import com.example.wen.pamvpdemo.http.ResponseInfoAPI;
import com.pa.core.base.BaseModel;
import com.pa.core.bean.TopResponse;

import io.reactivex.Flowable;

/**
 * Created by wen on 2018/5/14.
 */
public class LoginInteractorImpl extends BaseModel implements LoginContract.LoginInteractor {

    @Override
    public Flowable<TopResponse<Object>> getUserLogin() {
        return mRetrofit
                .create(ResponseInfoAPI.class)
                .getObject();
    }
}
