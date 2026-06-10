package com.yellowbee.gesturetools.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author huangfeng
 * @date 2023/12/14
 * @description
 * @since
 */
public class TimeUtils {

    /**
     * 传入时间区间，
     * @param interval
     * @return
     */
    public static boolean isCurrentTimeInInterval(String interval) {
        // 获取当前时间的时和分
        Date currentTime = new Date();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        String currentTimeStr = timeFormat.format(currentTime);

        // 将时间区间字符串拆分为开始时间和结束时间
        String[] intervalParts = interval.split("-");
        String startTimeStr = intervalParts[0].trim();
        String endTimeStr = intervalParts[1].trim();

        try {
            // 比较当前时间是否在区间内
            return currentTimeStr.compareTo(startTimeStr) >= 0 && currentTimeStr.compareTo(endTimeStr) <= 0;
        } catch (Exception e) {
            e.printStackTrace();
            // 如果发生异常，可以根据实际情况进行处理
            return false;
        }
    }
}
