package com.example.wen.pamvpdemo.login

import com.pa.core.base.IModel
import com.pa.core.base.IPresenter
import com.pa.core.base.IView
import com.pa.core.bean.TopResponse

import io.reactivex.Flowable

/**
 * Created by wen on 2018/5/14.
 */
interface LoginContract {

    interface LoginView : IView {

        fun onSuccess(any: Any)

        fun onFailure()

    }

    interface LoginInteractor : IModel {
        fun userLogin(): Flowable<TopResponse<Any>>
    }

    interface LoginPresenter : IPresenter {

        fun login()
    }
}
