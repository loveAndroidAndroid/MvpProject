package com.example.wen.pamvpdemo.login

import com.example.wen.pamvpdemo.R
import com.pa.core.base.BaseActivity
import com.pa.core.base.IPresenter

class LoginFragmentActivity : BaseActivity<LoginContract.LoginPresenter>(), LoginContract.LoginView {

    override fun onSuccess(any: Any) {
    }

    override fun onFailure() {
    }

    override fun getlayoutId(): Int {
        return R.layout.activity_login_fragment
    }

    override fun createPresenter(): IPresenter? {
        return null
    }

    override fun initView() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fl_container, LoginFragment(), null).commit()
    }
}
