package com.yellowbee.gesturetools.activity

import android.annotation.SuppressLint
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.yellowbee.gesturetools.receiver.TestBroadcastReceiver
import com.yellowbee.gesturetools.utils.MyLog

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private val debugReceiver: TestBroadcastReceiver = TestBroadcastReceiver()

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val filter = IntentFilter().apply {
            addAction(TestBroadcastReceiver.ACTION_CLICK)
            addAction(TestBroadcastReceiver.ACTION_BACK)
            addAction(TestBroadcastReceiver.ACTION_DRAG)
            addAction(TestBroadcastReceiver.ACTION_SWIPE)
            addAction(TestBroadcastReceiver.ACTION_HOME)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(
                debugReceiver,
                filter,
                RECEIVER_EXPORTED
            )
            MyLog.i(TAG, "register receiver")
        } else {
            registerReceiver(
                debugReceiver,
                filter
            )
            MyLog.i(TAG, "register receiver")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(debugReceiver)
    }
}