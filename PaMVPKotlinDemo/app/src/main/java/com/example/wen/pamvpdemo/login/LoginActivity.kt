package com.example.wen.pamvpdemo.login

import android.view.View
import android.widget.Toast
import com.example.wen.pamvpdemo.R
import com.pa.core.base.BaseActivity
import com.pa.core.base.IPresenter
//省去了findviefyid的工作
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity : BaseActivity<LoginContract.LoginPresenter>(), LoginContract.LoginView, View.OnClickListener {

    override fun getlayoutId(): Int {
        return R.layout.activity_main
    }

    override fun createPresenter(): IPresenter {
        return LoginPresenterImpl(this, LoginInteractorImpl())
    }

    override fun initData() {}

    //?. 防止空指针  如果为空 就不往下执行了
    override fun initView() {
        button?.setOnClickListener(this)
    }

    override fun initTitle() {}

    //登录
    override fun onClick(v: View) {
        mPresenter?.login()
    }

    override fun onSuccess(any: Any) {
        Toast.makeText(this, "success", Toast.LENGTH_LONG).show()
    }

    override fun onFailure() {
        Toast.makeText(this, "failure", Toast.LENGTH_LONG).show()
    }
}
