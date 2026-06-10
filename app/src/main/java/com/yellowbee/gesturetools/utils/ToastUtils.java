package com.yellowbee.gesturetools.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.widget.Toast;

public class ToastUtils {

    private static final Handler handler = new Handler(Looper.getMainLooper());

    public static void toast(final Context context, final String text) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                showCustomFontSizeToast(context, text, 30);
            }
        });
    }

    public static void toast(final Context context, final int resId) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void showCustomFontSizeToast(Context context, String message, int textSize) {
        // 创建一个新的 Toast 对象
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);

        // 获取 Toast 的 View
        /*View toastView = toast.getView();

        // 获取 TextView
        TextView toastTextView = toastView.findViewById(android.R.id.message);

        // 设置 TextView 的文字大小
        toastTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);*/

        // 设置 Toast 在屏幕中央
        toast.setGravity(Gravity.CENTER, 0, 0);
        // 显示 Toast
        toast.show();
    }
}
