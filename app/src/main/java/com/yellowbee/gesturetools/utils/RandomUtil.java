package com.yellowbee.gesturetools.utils;

import java.security.SecureRandom;

/**
 * @author huangfeng
 * @date 2024/3/5
 * @description 随机工具
 * @since
 */
public class RandomUtil {
    public static final String BASE_CHAR_NUMBER = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String randomString(String base, int length) {
        StringBuilder sb = new StringBuilder(length);
        SecureRandom random = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(base.length());
            char randomChar = base.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }

}
