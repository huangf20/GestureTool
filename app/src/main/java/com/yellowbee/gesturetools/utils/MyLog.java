package com.yellowbee.gesturetools.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MyLog {

    public static boolean isWriteLog = true;//是否将log写入文件
    public static boolean isShowLog = true;//是否输出显示log
    public static boolean isLogToDb = true;//是否将log写入数据库
    private static final String TAG = "MyLog";
    private static char MYLOG_TYPE = 'v';// 输入日志类型，w代表只输出告警信息等，v代表输出所有信息
    private static final String MYLOG_PATH_SDCARD_DIR = Environment.getExternalStorageDirectory().getAbsolutePath()  + "/gesture/log/";// 日志文件在sdcard中的路径
    public static int SDCARD_LOG_FILE_SAVE_DAYS = 7;// sd卡中日志文件的最多保存天数
    private static String MYLOGFILEName = "Log.txt";// 本类输出的日志文件名称
    private static SimpleDateFormat myLogSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 日志的输出格式
    private static SimpleDateFormat logfile = new SimpleDateFormat("yyyy-MM-dd");// 日志文件格式
    public Context context;

    /**
     * 单个打印最大长度
     */
    public static final int LOG_MAX_LENGTH = 1000;

    public static void w(String tag, Object msg) { // 警告信息
        log(tag, msg.toString(), 'w');
    }

    public static void e(String tag, Object msg) { // 错误信息
        log(tag, msg.toString(), 'e');
    }

    public static void d(String tag, Object msg) {// 调试信息
        log(tag, msg.toString(), 'd');
    }

    public static void i(String tag, Object msg) {//
        log(tag, msg.toString(), 'i');
    }

    public static void v(String tag, Object msg) {
        log(tag, msg.toString(), 'v');
    }

    public static void w(String tag, String text) {
        log(tag, text, 'w');
    }

    public static void e(String tag, String text) {
        log(tag, text, 'e');
    }

    public static void d(String tag, String text) {
        log(tag, text, 'd');
    }

    public static void i(String tag, String text) {
        log(tag, text, 'i');
    }

    public static void v(String tag, String text) {
        log(tag, text, 'v');
    }


    @SuppressLint("ConstantLocale")
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());

    public static void d(String msg) {
        log("D", msg);
    }

    public static void i(String msg) {
        log("I", msg);
    }

    public static void w(String msg) {
        log("W", msg);
    }

    public static void e(String msg) {
        if(TextUtils.isEmpty(msg)){
            msg = "empty";
        }
        log("E", msg);
    }

    public static void d() {
        log("D", "");
    }

    public static void i() {
        log("I", "");
    }

    public static void w() {
        log("W", "");
    }

    public static void e() {
        log("E", "");
    }

    public static void d(Object o) {
        log("D", JsonUtils.objectToJson(o));
    }

    public static void i(Object o) {
        log("I", JsonUtils.objectToJson(o));
    }

    public static void w(Object o) {
        log("W", JsonUtils.objectToJson(o));
    }

    public static void e(Object o) {
        log("E", JsonUtils.objectToJson(o));
    }

    @SuppressLint("CheckResult")
    private static synchronized void log(String level, String msg) {
        String currentTime = dateFormat.format(new Date());
        String className = getClassName();
        String methodName = getMethodName();
        String tag = className + "." + methodName;
        String logMessage = currentTime + " " + android.os.Process.myPid() + "-" + android.os.Process.myTid() + " " + level +
                " " + tag + "  " + msg;

        if ("E".equals(level)) { // 输出错误信息
            Log.e(tag, msg);
        } else if ("W".equals(level)) {
            Log.w(tag, msg);
        } else if ("D".equals(level)) {
            Log.d(tag, msg);
        } else if ("I".equals(level)) {
            Log.i(tag, msg);
        } else {
            Log.v(tag, msg);
        }
        if (isWriteLog) {
            ThreadPoolManager.getInstance().executeRunnable(()->{
                writeLogToFile(logMessage);
            });
        }

    }

    private static String getClassName() {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        if (elements.length >= 6) {
            String className = elements[5].getClassName();
            int lastIndex = className.lastIndexOf('.');
            if (lastIndex != -1 && lastIndex < className.length() - 1) {
                return className.substring(lastIndex + 1);
            }
        }
        return "UnknownClass";
    }

    private static String getMethodName() {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        if (elements.length >= 6) {
            return elements[5].getMethodName();
        }
        return "UnknownMethod";
    }

    /**
     * 根据tag, msg和等级，输出日志
     *
     * @param tag
     * @param msg
     * @param level
     */
    private static void log(String tag, String msg, char level) {
//        if (!BuildConfig.IS_DEBUG){
//            return;
//        }
        if (isWriteLog) {//日志文件总开关
            if ('e' == level && ('e' == MYLOG_TYPE || 'v' == MYLOG_TYPE)) { // 输出错误信息
                Log.e(tag, msg);
            } else if ('w' == level && ('w' == MYLOG_TYPE || 'v' == MYLOG_TYPE)) {
                Log.w(tag, msg);
            } else if ('d' == level && ('d' == MYLOG_TYPE || 'v' == MYLOG_TYPE)) {
                Log.d(tag, msg);
            } else if ('i' == level && ('d' == MYLOG_TYPE || 'v' == MYLOG_TYPE)) {
                Log.i(tag, msg);
            } else {
                Log.v(tag, msg);
            }
            if (isWriteLog)//日志写入文件开关
                writeLogtoFile(String.valueOf(level), tag, msg);
        }
    }

    /**
     * 打开日志文件并写入日志
     * @param content
     */
    private static void writeLogToFile(String content) {
        Date nowtime = new Date();
        String needWriteFiel = logfile.format(nowtime);
        File dirsFile = new File(MYLOG_PATH_SDCARD_DIR);
        if (!dirsFile.exists()) {
            dirsFile.mkdir();
        }
        //Log.i("创建文件","创建文件");
        File file = new File(dirsFile.toString(), needWriteFiel + MYLOGFILEName);// MYLOG_PATH_SDCARD_DIR
        if (!file.exists()) {
            try {
                //在指定的文件夹中创建文件
                file.createNewFile();
            } catch (Exception e) {
            }
        }

        try {
            FileWriter filerWriter = new FileWriter(file, true);// 后面这个参数代表是不是要接上文件中原来的数据，不进行覆盖
            BufferedWriter bufWriter = new BufferedWriter(filerWriter);
            bufWriter.write(content);
            bufWriter.newLine();
            bufWriter.close();
            filerWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开日志文件并写入日志
     *
     * @param mylogtype
     * @param tag
     * @param text
     */
    private static void writeLogtoFile(String mylogtype, String tag, String text) {
        Date nowtime = new Date();
        String needWriteFiel = logfile.format(nowtime);
        String needWriteMessage = myLogSdf.format(nowtime) + "    " + mylogtype + "    " + tag + "    " + text;
        File dirPath = Environment.getExternalStorageDirectory();

        File dirsFile = new File(MYLOG_PATH_SDCARD_DIR);
        if (!dirsFile.exists()) {
            dirsFile.mkdirs();
        }
        //Log.i("创建文件","创建文件");
        File file = new File(dirsFile.toString(), needWriteFiel + MYLOGFILEName);// MYLOG_PATH_SDCARD_DIR
        if (!file.exists()) {
            try {
                //在指定的文件夹中创建文件
                file.createNewFile();
            } catch (Exception e) {
            }
        }

        try {
            FileWriter filerWriter = new FileWriter(file, true);// 后面这个参数代表是不是要接上文件中原来的数据，不进行覆盖
            BufferedWriter bufWriter = new BufferedWriter(filerWriter);
            bufWriter.write(needWriteMessage);
            bufWriter.newLine();
            bufWriter.close();
            filerWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除制定的日志文件
     */
    public static void delFile() {// 删除日志文件
        try {
            long long2 = new Date().getTime();
            File fileAll = new File(MYLOG_PATH_SDCARD_DIR);
            if (fileAll.exists()) {
                File[] files = fileAll.listFiles();
                if (files != null && files.length > 0) {
                    for (File file1 : files) {
                        long long1 = logfile.parse(file1.getName().replace("Log", "")).getTime();
                        if (long2 - long1 > SDCARD_LOG_FILE_SAVE_DAYS * 24 * 3600 * 1000) {
                            file1.delete();
                            MyLog.i(TAG, "删除本地日志：" + file1.getName());
                        }
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


}

