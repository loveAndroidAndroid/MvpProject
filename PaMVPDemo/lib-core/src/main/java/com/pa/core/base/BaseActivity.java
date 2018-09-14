package com.pa.core.base;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


import com.pa.core.view.CustomProgressDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by wen on 2018/5/14.
 */
public abstract class BaseActivity<T extends IPresenter> extends AppCompatActivity implements IView {

    protected T mPresenter;
    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getlayoutId());
        mPresenter = (T) createPresenter();
        unbinder = ButterKnife.bind(this);
        initData();
        initView();
        initTitle();
    }

    protected abstract int getlayoutId();

    protected abstract IPresenter createPresenter();

    public void initData() {
    }

    public void initView() {
    }

    public void initTitle(){
    }


    @Override
    public void showLoadingView() {
        if (!this.isFinishing()&&!CustomProgressDialog.isShow()) {
            CustomProgressDialog.show(this, "登录中...", false, onCancelListener);
        }
    }

    @Override
    public void hideLoadingView() {
        if (!this.isFinishing()&&CustomProgressDialog.isShow()) {
            CustomProgressDialog.dimiss();
        }
    }

    @Override
    protected void onDestroy() {
        if (null != mPresenter) {
            mPresenter.onDestroy();
        }
        if (null != unbinder) {
            unbinder.unbind();
            unbinder = null;
        }
        super.onDestroy();
    }

    /**
     * 监听dialog的返回  取消后注销请求
     */
    OnCancelListener onCancelListener = new OnCancelListener() {
        @Override
        public void onCancel(DialogInterface dialog) {
            mPresenter.unRegisterDispose();
        }
    };
}
