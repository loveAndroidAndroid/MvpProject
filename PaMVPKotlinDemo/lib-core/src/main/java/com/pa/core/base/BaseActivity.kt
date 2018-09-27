package com.pa.core.base


import android.content.DialogInterface.OnCancelListener
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.pa.core.view.CustomProgressDialog


/**
 * Created by wen on 2018/5/14.
 */
abstract class BaseActivity<T : IPresenter> : AppCompatActivity(), IView {

    protected var mPresenter: T? = null

    /**
     * 监听dialog的返回  取消后注销请求
     */
    internal var onCancelListener: OnCancelListener = OnCancelListener { mPresenter?.unRegisterDispose() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getlayoutId())
        //如果转换是不可能的，转换操作符会抛出一个异常。因此，我们称之为不安全的。 Kotlin 中的不安全转换由中缀操作符 as
        //为了避免抛出异常，可以使用安全转换操作符 as?，它可以在失败时返回 null：
        mPresenter = createPresenter() as? T
        initData()
        initView()
        initTitle()
    }

    protected abstract fun getlayoutId(): Int

    protected abstract fun createPresenter(): IPresenter?

    open fun initData() {}

    open fun initView() {}

    open fun initTitle() {}


    override fun showLoadingView() {
        if (!this.isFinishing && !CustomProgressDialog.isShow) {
            CustomProgressDialog.show(this, "登录中...", false, onCancelListener)
        }
    }

    override fun hideLoadingView() {
        if (!this.isFinishing && CustomProgressDialog.isShow) {
            CustomProgressDialog.dimiss()
        }
    }

    override fun onDestroy() {
        if (null != mPresenter) {
            mPresenter?.onDestroy()
        }
        super.onDestroy()
    }
}
