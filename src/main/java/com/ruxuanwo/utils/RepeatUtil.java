package com.ruxuanwo.utils;

import java.util.Iterator;
import java.util.TreeSet;

/**
 * 字符串去重
 *
 * @author 如漩涡
 */
public class RepeatUtil {
    private RepeatUtil(){

    }

    /**
     * 有排序的去重
     * @param str
     * @return
     */
    public static String removeRepeat(String str) {
        TreeSet<String> strings = new TreeSet<>();
        for (int i = 0; i < str.length(); i++) {
            strings.add(str.charAt(i) + "");
        }
        Iterator<String> iterator = strings.iterator();
        StringBuilder builder = new StringBuilder();
        while (iterator.hasNext()) {
            builder.append(iterator.next());
        }
        return builder.toString();
    }

    /**
     * 没有排序的去重
     * @param str
     * @return
     */
    public static String returnRepeat(String str) {
        int len = str.length();
        int count = 0 , k;
        String data = "";
        char[] c = new char[len];
        for (int i = 0; i < len; i++) {
            c[i] = str.charAt(i);
        }
        for (int i = 0; i < len; i++) {
            k = i + 1;
            while (k < len - count) {
                if (c[i] == c[k]) {
                    for (int j = k; j < len - 1; j++) {
                        //出现重复字母，从k位置开始将数组往前挪位
                        c[j] = c[j + 1];
                    }
                    //重复字母出现的次数
                    count++;
                    k--;
                }
                k++;
            }
        }
        for (int i = 0; i < len - count; i++) {
            data += String.valueOf(c[i]);
        }
        return data;
    }

    public static void main(String[] args) {
        System.out.println(removeRepeat("ddddccffgd"));
        System.out.println(returnRepeat("ddddccffgd"));
    }
}
