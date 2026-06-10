package com.yellowbee.gesturetools.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.yellowbee.gesturetools.helper.AccessibilityHelper
import com.yellowbee.gesturetools.utils.MyLog

class TestBroadcastReceiver: BroadcastReceiver(){
    companion object{
        private const val TAG = "TestBroadcastReceiver"
        const val  ACTION_CLICK = "com.yellowbee.action_click"
        const val  ACTION_DRAG = "com.yellowbee.action_drag"
        const val  ACTION_BACK = "com.yellowbee.action_back"
        const val  ACTION_SWIPE  = "com.yellowbee.action_swipe"
        const val  ACTION_HOME  = "com.yellowbee.action_home"

    }



    override fun onReceive(
        context: Context?,
        intent: Intent?
    ) {

        val action = intent?.action
        MyLog.d(TAG, "receive action: $action")
        when(action){

            ACTION_CLICK -> {

                val x = intent.getFloatExtra("x", 500f)
                val y = intent.getFloatExtra("y", 1000f)

                AccessibilityHelper.click(x,y)
            }

            ACTION_SWIPE -> {

                val startX = intent.getFloatExtra("startX", 300f)
                val startY = intent.getFloatExtra("startY", 1000f)
                val endX = intent.getFloatExtra("endX", 900f)
                val endY = intent.getFloatExtra("endY", 1000f)

                AccessibilityHelper.swipe(
                        startX,
                        startY,
                        endX,
                        endY,
                        300
                    )
            }

            ACTION_BACK -> {
                AccessibilityHelper.back()
            }

            ACTION_HOME -> {
                AccessibilityHelper.home()
            }
        }
    }

}