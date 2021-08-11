package com.funpay.demo.utils;

import java.util.Random;

/**
 * RandomUtil
 * 随机工具类
 * Created by Common on 2020/11/11.
 * @author FunPay
 */
public class RandomUtil {
    /**
     * 仅包含数字字母的随机字符串
     *
     * @param length 字符串长度
     * @return 生成字符串
     */
    public static String charDigits(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            stringBuffer.append(str.charAt(number));
        }
        return stringBuffer.toString();
    }

    /**
     * 仅包含数字的随机字符串
     *
     * @param length 字符串长度
     * @return 生成字符串
     */
    public static String digits(int length) {
        String str = "0123456789";
        Random random = new Random();
        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            stringBuffer.append(str.charAt(number));
        }
        return stringBuffer.toString();
    }
}
