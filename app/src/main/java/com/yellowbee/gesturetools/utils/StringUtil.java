package com.yellowbee.gesturetools.utils;

/**
 * @author huangfeng
 * @date 2024/5/7
 * @description
 * @since
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    /**
     * 判断是否为整数
     * @param str 传入的字符串
     * @return 是整数返回true,否则返回false
     */
    public static boolean isInteger(String str) {
        if (isEmpty(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断是否为摄像头角度
     */
    public static boolean isCameraDegree(String s){
        if (isEmpty(s) || !isInteger(s)) {
            return false;
        }
        return "0".equals(s) || "90".equals(s) || "180".equals(s) || "270".equals(s);
    }

    /**
     * 判断字符串是否为空
     * @param str 待判断的字符串
     * @return 字符串为空返回true，否则返回false
     */
    private static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * 判断字符串是否身份证
     * @param id 待判断的字符串
     * @return
     */
    public static boolean isChineseID(String id) {
        // 正则表达式匹配中国身份证号码格式
        String pattern = "(^[1-9]\\d{5}(18|19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[1-2]\\d|3[0-1])\\d{3}([0-9Xx])$)";
        // 编译正则表达式
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(id);
        // 判断是否匹配成功
        return matcher.matches();
    }

    public static boolean isAlphanumeric(String str) {
        return str != null && str.matches("[a-zA-Z0-9]+");
    }

    /**
     * 替换区间文本
     * @param str
     * @param start
     * @param end
     * @param replacement
     * @return
     */
    public static String replaceRange(String str, int start, int end, String replacement) {
        StringBuilder sb = new StringBuilder(str);
        sb.replace(start, end, replacement);
        return sb.toString();
    }

    public static boolean isValidTimeRange(String input) {
        // 正则表达式，匹配 hh:mm-hh:mm 格式
        String timePattern = "^([01]\\d|2[0-3]):([0-5]\\d)-([01]\\d|2[0-3]):([0-5]\\d)$";
        Pattern pattern = Pattern.compile(timePattern);
        Matcher matcher = pattern.matcher(input);

        if (!matcher.matches()) {
            return false;
        }

        // 解析时间部分
        String[] times = input.split("-");
        String[] startTime = times[0].split(":");
        String[] endTime = times[1].split(":");

        int startHour = Integer.parseInt(startTime[0]);
        int startMinute = Integer.parseInt(startTime[1]);
        int endHour = Integer.parseInt(endTime[0]);
        int endMinute = Integer.parseInt(endTime[1]);

        // 比较时间
        if (endHour > startHour || (endHour == startHour && endMinute > startMinute)) {
            return true;
        } else {
            return false;
        }
    }
}

