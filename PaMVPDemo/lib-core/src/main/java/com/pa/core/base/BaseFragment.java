package com.pa.core.base;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.pa.core.view.CustomProgressDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zhangxiaowen on 2018/8/7.
 */

public abstract class BaseFragment<T extends IPresenter> extends Fragment implements IView {

    protected T mPresenter;
    private Unbinder unbinder;
    public Activity mActivity;

    @Override
    public void onAttach(Activity activity) {
        mActivity = activity;
        super.onAttach(activity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(container.getContext()).inflate(getlayoutId(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this,view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter = (T) createPresenter();
        initTitle();
        initData();
        initView();
    }

    protected abstract int getlayoutId();

    protected abstract IPresenter createPresenter();

    public void initData() {
    }

    public void initView() {
    }

    public void initTitle() {
    }


    @Override
    public void showLoadingView() {
        if (!getActivity().isFinishing()) {
            CustomProgressDialog.show(getActivity(), "登录中...", false, onCancelListener);
        }
    }

    @Override
    public void hideLoadingView() {
        if (!getActivity().isFinishing()) {
            CustomProgressDialog.dimiss();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivity = null;
    }

    @Override
    public void onDestroy() {
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
    DialogInterface.OnCancelListener onCancelListener = new DialogInterface.OnCancelListener() {
        @Override
        public void onCancel(DialogInterface dialog) {
            mPresenter.unRegisterDispose();
        }
    };
}
