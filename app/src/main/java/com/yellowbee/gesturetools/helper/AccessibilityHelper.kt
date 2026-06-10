package com.yellowbee.gesturetools.helper

import com.yellowbee.gesturetools.service.GestureAccessibilityService

object AccessibilityHelper {

    fun click(x: Float, y: Float) {
        GestureAccessibilityService.instance
            ?.click(x, y)
    }

    fun swipe(
        startX: Float,
        startY: Float,
        endX: Float,
        endY: Float,
        duration: Long = 300
    ) {
        GestureAccessibilityService.instance
            ?.swipe(
                startX,
                startY,
                endX,
                endY,
                duration
            )
    }

    fun back() {
        GestureAccessibilityService.instance
            ?.back()
    }

    fun home() {
        GestureAccessibilityService.instance
            ?.home()
    }
}