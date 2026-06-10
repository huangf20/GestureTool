package com.yellowbee.gesturetools.service

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.app.ForegroundServiceStartNotAllowedException
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Path
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import androidx.core.app.NotificationCompat
import com.yellowbee.gesturetools.R
import com.yellowbee.gesturetools.utils.MyLog

/**
 * 无障碍服务
 */
class GestureAccessibilityService : AccessibilityService() {



    override fun onAccessibilityEvent(event: AccessibilityEvent?) {

    }

    override fun onInterrupt() {

    }



    override fun onServiceConnected() {
        super.onServiceConnected()
        instance = this
    }

    override fun onDestroy() {
        instance = null
        super.onDestroy()
    }

    /**
     * 点击事件
     */
    fun click(x: Float, y: Float) {

        val path = Path().apply {
            moveTo(x, y)
        }

        val gesture = GestureDescription.Builder()
            .addStroke(
                GestureDescription.StrokeDescription(
                    path,
                    0,
                    50
                )
            )
            .build()

        dispatchGesture(
            gesture,
            object : GestureResultCallback() {

                override fun onCompleted(
                    gestureDescription: GestureDescription?
                ) {
                    super.onCompleted(gestureDescription)
                    MyLog.d(TAG, "点击成功")
                }

                override fun onCancelled(
                    gestureDescription: GestureDescription?
                ) {
                    super.onCancelled(gestureDescription)
                    MyLog.d(TAG, "点击取消")
                }
            },
            null
        )
    }

    /**
     * 长按事件
     */
    fun longClick(
        x: Float,
        y: Float,
        duration: Long = 1000
    ) {

        val path = Path().apply {
            moveTo(x, y)
        }

        val gesture = GestureDescription.Builder()
            .addStroke(
                GestureDescription.StrokeDescription(
                    path,
                    0,
                    duration
                )
            )
            .build()

        dispatchGesture(
            gesture,
            null,
            null
        )
    }

    /**
     * 模拟滑动
     */
    fun swipe(
        startX: Float,
        startY: Float,
        endX: Float,
        endY: Float,
        duration: Long = 300
    ) {

        val path = Path().apply {
            moveTo(startX, startY)
            lineTo(endX, endY)
        }

        val gesture = GestureDescription.Builder()
            .addStroke(
                GestureDescription.StrokeDescription(
                    path,
                    0,
                    duration
                )
            )
            .build()

        dispatchGesture(
            gesture,
            null,
            null
        )
    }

    /**
     * 模拟拖拽
     */
    fun drag(
        startX: Float,
        startY: Float,
        endX: Float,
        endY: Float
    ) {
        swipe(
            startX,
            startY,
            endX,
            endY,
            1200
        )
    }

    /**
     * 返回键
     */
    fun back() {
        performGlobalAction(
            GLOBAL_ACTION_BACK
        )
    }

    /**
     * home键
     */
    fun home() {
        performGlobalAction(
            GLOBAL_ACTION_HOME
        )
    }

    /**
     * 最近任务
     */
    fun recents() {
        performGlobalAction(
            GLOBAL_ACTION_RECENTS
        )
    }

    /**
     * 通知栏
     */
    fun notifications() {
        performGlobalAction(
            GLOBAL_ACTION_NOTIFICATIONS
        )
    }





    companion object{

        private const val TAG = "GestureAccessibilitySer"

        /**
         * 全局实例
         */
        @Volatile
        var instance: GestureAccessibilityService? = null
            private set

    }
}