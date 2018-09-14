package com.pa.core.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.pa.core.R;

/**
 * Created by zhangxiaowen on 2018/7/12.
 */
public class CustomProgressDialog extends Dialog {
    private static CustomProgressDialog dialog;

    public CustomProgressDialog(Context context) {
        super(context);
    }

    public CustomProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    /**
     * 弹出自定义ProgressDialog
     *
     * @param context        上下文
     * @param message        提示
     * @param cancelable     是否可取消
     * @param cancelListener 按下返回键监听
     * @return
     */
    public static CustomProgressDialog show(Context context, CharSequence message, boolean cancelable, OnCancelListener cancelListener) {

        dialog = new CustomProgressDialog(context, R.style.Custom_Progress);
        dialog.setTitle("");
        dialog.setContentView(R.layout.layout_progress_dialog);

        if (message == null || message.length() == 0) {
            dialog.findViewById(R.id.tv_message).setVisibility(View.GONE);
        } else {
            TextView tv_message = (TextView) dialog.findViewById(R.id.tv_message);
            tv_message.setText(message);
        }
        // 按返回键是否取消
        dialog.setCanceledOnTouchOutside(cancelable);
        // 监听返回键处理
        dialog.setOnCancelListener(cancelListener);
        // 设置居中
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        // 设置背景层透明度
        lp.dimAmount = 0.1f;
        dialog.getWindow().setAttributes(lp);
        dialog.show();

        return dialog;
    }

    /**
     * 关闭dialog
     */
    public static void dimiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    /**
     * dialog状态
     */
    public static boolean isShow() {
        if (dialog != null && dialog.isShowing()) {
            return true;
        }
        return false;
    }
}

