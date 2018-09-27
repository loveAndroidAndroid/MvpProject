package com.example.wen.pamvpdemo.login

import android.view.View
import android.widget.Toast
import com.example.wen.pamvpdemo.R
import com.pa.core.base.BaseFragment
import com.pa.core.base.IPresenter
import com.pa.core.base.IView
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by zhangxiaowen on 2018/8/7.
 */

class LoginFragment : BaseFragment<LoginContract.LoginPresenter>(), LoginContract.LoginView, View.OnClickListener, IView {

    override fun getlayoutId(): Int {
        return R.layout.activity_main
    }

    override fun createPresenter(): IPresenter {
        return LoginPresenterImpl(this, LoginInteractorImpl())
    }

    override fun initData() {}

    override fun initView() {
        button!!.setOnClickListener(this)
    }

    override fun initTitle() {}

    //登录
    override fun onClick(v: View) {
        mPresenter?.login()
    }

    override fun onSuccess(`object`: Any) {
        Toast.makeText(activity, "success", Toast.LENGTH_LONG).show()
    }

    override fun onFailure() {
        Toast.makeText(activity, "failure", Toast.LENGTH_LONG).show()
    }

}
