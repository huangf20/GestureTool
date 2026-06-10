package com.yellowbee.gesturetools.service

import android.app.ForegroundServiceStartNotAllowedException
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.yellowbee.gesturetools.R
import com.yellowbee.gesturetools.utils.MyLog

class FloatingWindowService : Service() {

    companion object{
        private const val TAG = "FloatingWindowService"
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        startForeground()
    }

    private fun startForeground() {
        try {
            // 确保在 Android 8.0 及以上版本中创建通知渠道
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel()
            }
            // 创建通知
            val notification: Notification = NotificationCompat.Builder(this, "gesture_accessibility_service")
                .setContentTitle("手势工具")
                .setContentText("手势工具正在运行的通知")
                .setSmallIcon(R.mipmap.welcome_icon)
                .build()
            // 启动前台服务
            startForeground(100, notification)
        } catch (e: Exception) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
                && e is ForegroundServiceStartNotAllowedException
            ) {
                MyLog.e(TAG, e.message)
            }
        }
    }


    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "gesture_accessibility_service"
            val channelName = "手势工具"
            val channelDescription = "手势工具正在运行的通知"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val notificationChannel = NotificationChannel(channelId, channelName, importance).apply {
                description = channelDescription
            }

            val notificationManager: NotificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

}