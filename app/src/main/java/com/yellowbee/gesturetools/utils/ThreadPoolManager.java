package com.yellowbee.gesturetools.utils;


import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author huangfeng
 * @version 1.0.0
 * @date 2023/5/26
 * @description 线程池统一管理
 * @since 金沙智拍
 */
public class ThreadPoolManager {
    private static final String TAG = "ThreadPoolManager";
    private ExecutorService mExecutorService; // 线程池对象

    /**
     * 主线程信使
     */
    private Handler mainHandler;

    /**
     * 类级的内部类，也就是静态类的成员式内部类，该内部类的实例与外部类的实例
     * 没有绑定关系，而且只有被调用时才会装载，从而实现了延迟加载
     */
    private static class ThreadPoolManagerHolder {
        /**
         * 静态初始化器，由JVM来保证线程安全
         */
        private static ThreadPoolManager instance = new ThreadPoolManager();
    }

    /**
     * 私有化构造方法
     */
    private ThreadPoolManager() {
    }

    public static ThreadPoolManager getInstance() {
        return ThreadPoolManagerHolder.instance;
    }

    /**
     * 初始化线程池
     */
    public void initThreadPool() {
        if (mExecutorService == null) {
            mExecutorService = Executors.newCachedThreadPool();
        }
        if(mainHandler == null){
            mainHandler = new Handler(Looper.getMainLooper());
        }
    }

    /**
     * 执行任务
     *
     * @param runnable 任务
     */
    public void executeRunnable(Runnable runnable) {
        try {
            if (mExecutorService != null && !mExecutorService.isShutdown()) {
                mExecutorService.execute(runnable);
            }
        } catch (RejectedExecutionException e) {
            // 打印当前线程池中活跃的线程数量
            int threadCount = ((ThreadPoolExecutor) mExecutorService).getActiveCount();
            MyLog.d(TAG,
                    "[executeRunable]: current alive thread count = " + threadCount);
            e.printStackTrace();
        }
    }

    /**
     * 执行任务
     */
    public void execute(Runnable runnable){
        executeRunnable(runnable);
    }

    /**
     * 主线程执行任务
     */
    public void executeOnMain(Runnable runnable){
        mainHandler.post(runnable);
    }



    /**
     * 关闭线程池
     */
    public void shutdownExecutor() {
        if (mExecutorService != null && !mExecutorService.isShutdown()) {
            mExecutorService.shutdown();
            mExecutorService = null;
        }
        if(mainHandler != null){
            mainHandler.removeCallbacksAndMessages(null);
            mainHandler = null;
        }
    }
}
