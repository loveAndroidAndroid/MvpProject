package com.pa.core.base

import android.app.Activity
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pa.core.view.CustomProgressDialog

/**
 * Created by zhangxiaowen on 2018/8/7.
 */

abstract class BaseFragment<T : IPresenter> : Fragment(), IView {

    protected var mPresenter: T? = null
    var mActivity: Activity? = null

    /**
     * 监听dialog的返回  取消后注销请求
     */
    internal var onCancelListener: DialogInterface.OnCancelListener = DialogInterface.OnCancelListener { mPresenter?.unRegisterDispose() }

    override fun onAttach(activity: Activity?) {
        mActivity = activity
        super.onAttach(activity)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(container!!.context).inflate(getlayoutId(), container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mPresenter = createPresenter() as? T
        initTitle()
        initData()
        initView()
    }

    protected abstract fun getlayoutId(): Int

    protected abstract fun createPresenter(): IPresenter

    open fun initData() {}

    open fun initView() {}

    open fun initTitle() {}


    override fun showLoadingView() {
        if (!activity.isFinishing) {
            CustomProgressDialog.show(activity, "登录中...", false, onCancelListener)
        }
    }

    override fun hideLoadingView() {
        if (!activity.isFinishing) {
            CustomProgressDialog.dimiss()
        }
    }

    override fun onDetach() {
        super.onDetach()
        mActivity = null
    }

    override fun onDestroy() {
        if (null != mPresenter) {
            mPresenter?.onDestroy()
        }
        super.onDestroy()
    }
}
