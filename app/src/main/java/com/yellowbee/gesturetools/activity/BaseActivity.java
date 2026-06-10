package com.yellowbee.gesturetools.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author huangfeng
 * @date 2023/10/25
 * @description Activity基类
 * @since
 */
    public abstract class BaseActivity<VD extends ViewDataBinding, VM extends ViewModel> extends AppCompatActivity {

    private static final String TAG = "BaseActivity";

    /**
     * 携带值key
     */
    private static final String INTENT_EXTRA_DATA = "INTENT_EXTRA_DATA";

    /**
     * ViewBinding
     */
    protected VD mBinding;

    /**
     * ViewBinding
     */
    protected VM mViewModel;

    /**
     * AndroidX替代onActivityResult
     */
    private final ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            this::onActivityForResult
    );

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, getLayoutId());
        mViewModel = new ViewModelProvider(this).get(getViewModelClass());
        setImmersiveStatusBar();
        init();
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        release();
    }

    /**
     * 返回LayoutId即可
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 返回ViewModel Class对象
     * @return
     */
    protected  Class<VM> getViewModelClass(){
        Class<VM> viewModelClass = null;
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            if (actualTypeArguments.length > 1 && actualTypeArguments[1] instanceof Class) {
                viewModelClass = (Class<VM>) actualTypeArguments[1];
            } else {
                throw new IllegalArgumentException("Failed to determine the generic type");
            }
        } else {
            throw new IllegalArgumentException("Failed to determine the generic type");
        }
        return viewModelClass;
    }

    /**
     * 留给子Activity实现初始化
     */
    protected abstract void init();

    /**
     * 留给子Activity实现初始化
     */
    protected abstract void initView();

    /**
     * 留给子Activity实现资源释放
     */
    protected abstract void release();

    /**
     * AndroidX替代onActivityResult回调
     */
    protected void onActivityForResult(ActivityResult result){

    }

    /**
     * 设置沉浸式状态栏
     */
    private void setImmersiveStatusBar() {
        Window window = getWindow();
        View decorView = window.getDecorView();

        // 隐藏状态栏
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // 隐藏导航栏
               // | View.SYSTEM_UI_FLAG_FULLSCREEN // 隐藏状态栏
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY; // 沉浸式模式
        decorView.setSystemUiVisibility(uiOptions);

        // 设置状态栏颜色与布局背景相同
        window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
        window.setNavigationBarColor(getResources().getColor(android.R.color.transparent));

        // 使布局延伸到状态栏下
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.flags |= WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        window.setAttributes(attrs);
    }

    protected void showMsg(CharSequence msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected void showLongMsg(CharSequence msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    /**
     * 跳转页面
     * @param clazz 目标页面
     */
    protected void jumpActivity(final Class<?> clazz) {
        startActivity(new Intent(this, clazz));
    }

    /**
     * 跳转页面并关闭当前页面
     * @param clazz 目标页面
     */
    protected void jumpActivityFinish(final Class<?> clazz) {
        startActivity(new Intent(this, clazz));
        finish();
    }

    /**
     * 跳转页面并携带轻量数据
     */
    protected void jumpActivity(final Class<?> clazz, Object map){
        Intent intent = new Intent(this, clazz);
//        intent.putExtra(INTENT_EXTRA_DATA, JsonUtils.objectToJson(map));
        startActivity(intent);
    }

    /**
     * 跳转页面并携带轻量数据
     */
    protected void jumpActivityWithInt(final Class<?> clazz, int data){
        Intent intent = new Intent(this, clazz);
        intent.putExtra(INTENT_EXTRA_DATA, data);
        startActivity(intent);
    }

    /**
     * 跳转页面并关闭当前页面携带轻量数据
     * @param clazz 目标页面
     */
    protected void jumpActivityFinish(final Class<?> clazz, Object map) {
        Intent intent = new Intent(this, clazz);
//        intent.putExtra(INTENT_EXTRA_DATA, JsonUtils.objectToJson(map));
        startActivity(intent);
        finish();
    }
    /**
     * 获取额外值
     */
    protected  String getIntentString(){
        String stringExtra = getIntent().getStringExtra(INTENT_EXTRA_DATA);
        return stringExtra;
    }

    /**
     * 获取额外值
     */
    protected  int getIntentInt(){
        int stringExtra = getIntent().getIntExtra(INTENT_EXTRA_DATA, 0);
        return stringExtra;
    }

    /**
     * 跳转页面并取结果
     */
    protected void jumpActivityForResult(Class<?> clazz){
        someActivityResultLauncher.launch(new Intent(this, clazz));
    }

    /**
     * 设置状态栏图标颜色深色
     */
    protected void setStatusBarDark(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
//            MyLog.e(TAG, "android version is too low, can not set status bar !");
        }
    }

    /**
     * 设置状态栏图标颜色浅色
     */
    protected void setStatusBarLight(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        } else {
//            MyLog.e(TAG, "android version is too low, can not set status bar !");
        }
    }

    /**
     * 隐藏软键盘
     */
    protected void hideKeyboard(){
        InputMethodManager imm = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        }
        if(imm != null){
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

    }
}
