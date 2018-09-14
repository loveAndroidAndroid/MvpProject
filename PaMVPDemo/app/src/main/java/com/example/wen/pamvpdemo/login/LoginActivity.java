package com.example.wen.pamvpdemo.login;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.wen.pamvpdemo.R;
import com.pa.core.base.BaseActivity;
import com.pa.core.base.IPresenter;

import butterknife.BindView;

public class LoginActivity extends BaseActivity<LoginContract.LoginPresenter> implements LoginContract.LoginView, View.OnClickListener {

    @BindView(R.id.button)
    Button button;

    @Override
    protected int getlayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected IPresenter createPresenter() {
        return new LoginPresenterImpl(this, new LoginInteractorImpl());
    }

    @Override
    public void initData() {
    }

    @Override
    public void initView() {
        button.setOnClickListener(this);
    }

    @Override
    public void initTitle() {
    }

    //登录
    @Override
    public void onClick(View v) {
        mPresenter.login();
    }

    @Override
    public void onSuccess(Object object) {
        Toast.makeText(this, "success", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFailure() {
        Toast.makeText(this, "failure", Toast.LENGTH_LONG).show();
    }
}
