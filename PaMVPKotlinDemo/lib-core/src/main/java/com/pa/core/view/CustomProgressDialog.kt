package com.pa.core.view

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.TextView

import com.pa.core.R

/**
 * Created by zhangxiaowen on 2018/7/12.
 */
class CustomProgressDialog : Dialog {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, theme: Int) : super(context, theme) {}

    companion object {
        private var dialog: CustomProgressDialog? = null

        /**
         * 弹出自定义ProgressDialog
         *
         * @param context        上下文
         * @param message        提示
         * @param cancelable     是否可取消
         * @param cancelListener 按下返回键监听
         * @return
         */
        fun show(context: Context, message: CharSequence?, cancelable: Boolean, cancelListener: DialogInterface.OnCancelListener): CustomProgressDialog {

            dialog = CustomProgressDialog(context, R.style.Custom_Progress)
            dialog?.setTitle("")
            dialog?.setContentView(R.layout.layout_progress_dialog)

            if (message == null || message.length == 0) {
                dialog?.findViewById<View>(R.id.tv_message)?.visibility = View.GONE
            } else {
                val tv_message = dialog?.findViewById<View>(R.id.tv_message) as TextView
                tv_message.text = message
            }
            // 按返回键是否取消
            dialog?.setCanceledOnTouchOutside(cancelable)
            // 监听返回键处理
            dialog?.setOnCancelListener(cancelListener)
            // 设置居中
            dialog?.window?.attributes?.gravity = Gravity.CENTER
            val lp = dialog?.window?.attributes
            // 设置背景层透明度
            lp?.dimAmount = 0.1f
            dialog?.window?.attributes = lp
            dialog?.show()

            return dialog as CustomProgressDialog
        }

        /**
         * 关闭dialog
         */
        fun dimiss() {
            if (dialog != null && dialog?.isShowing!!) {
                dialog?.dismiss()
            }
        }

        /**
         * dialog状态
         */
        val isShow: Boolean
            get() = if (dialog != null && dialog?.isShowing!!) {
                true
            } else false
    }
}

