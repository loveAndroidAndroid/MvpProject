package com.example.wen.pamvpdemo.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.wen.pamvpdemo.R;
import com.pa.core.base.BaseFragment;
import com.pa.core.base.IPresenter;
import com.pa.core.base.IView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zhangxiaowen on 2018/8/7.
 */

public class LoginFragment extends BaseFragment<LoginContract.LoginPresenter> implements LoginContract.LoginView, View.OnClickListener, IView {


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
        Toast.makeText(getActivity(), "success", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFailure() {
        Toast.makeText(getActivity(), "failure", Toast.LENGTH_LONG).show();
    }

}
