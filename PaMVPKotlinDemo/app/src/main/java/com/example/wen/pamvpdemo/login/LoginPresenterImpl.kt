package com.example.wen.pamvpdemo.login

import com.pa.core.base.BasePresenter
import com.pa.core.bean.TopResponse
import com.pa.network.http.ApiSubscriberCallBack

import io.reactivex.Flowable

/**
 * Created by wen on 2018/5/14.
 */
class LoginPresenterImpl(private val loginView: LoginContract.LoginView,
                         private val loginInteractor: LoginContract.LoginInteractor) : BasePresenter<LoginContract.LoginInteractor, LoginContract.LoginView>(loginInteractor, loginView), LoginContract.LoginPresenter {
    private var userLogin: Flowable<TopResponse<Any>>? = null

    override fun login() {
        view!!.showLoadingView()
        userLogin = loginInteractor.userLogin()
        val apiSubscriberCallBack = object : ApiSubscriberCallBack<Any>() {

            override fun onSuccess(any: Any) {
                loginView.onSuccess(any)
                view?.hideLoadingView()
            }

            override fun onFailure(t: Throwable) {
                loginView.onFailure()
                view?.hideLoadingView()
            }
        }
        subscribe(userLogin!!, apiSubscriberCallBack)
    }
}
