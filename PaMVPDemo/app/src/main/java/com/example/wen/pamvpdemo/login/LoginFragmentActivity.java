package com.example.wen.pamvpdemo.login;

import android.widget.FrameLayout;

import com.example.wen.pamvpdemo.R;
import com.pa.core.base.BaseActivity;
import com.pa.core.base.IPresenter;

import butterknife.BindView;

public class LoginFragmentActivity extends BaseActivity {

    @BindView(R.id.fl_container)
    FrameLayout flContainer;

    @Override
    protected int getlayoutId() {
        return R.layout.activity_login_fragment;
    }

    @Override
    protected IPresenter createPresenter() {
        return null;
    }

    @Override
    public void initView() {
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fl_container,new LoginFragment(),null).commit();
    }
}
