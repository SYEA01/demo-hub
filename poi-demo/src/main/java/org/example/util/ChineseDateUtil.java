package org.example.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Taoao_
 * @create: 2025-08-20 11:13
 * @desc:
 */
public class ChineseDateUtil {// 数字到汉字的映射
    private static final Map<Character, String> digitMap = new HashMap<>();
    private static final Map<Character, String> monthMap = new HashMap<>();
    private static final Map<Character, String> dayMap = new HashMap<>();

    static {
        // 初始化数字映射
        digitMap.put('0', "〇");
        digitMap.put('1', "一");
        digitMap.put('2', "二");
        digitMap.put('3', "三");
        digitMap.put('4', "四");
        digitMap.put('5', "五");
        digitMap.put('6', "六");
        digitMap.put('7', "七");
        digitMap.put('8', "八");
        digitMap.put('9', "九");

        // 月份特殊映射
        monthMap.put('1', "一");
        monthMap.put('2', "二");
        monthMap.put('3', "三");
        monthMap.put('4', "四");
        monthMap.put('5', "五");
        monthMap.put('6', "六");
        monthMap.put('7', "七");
        monthMap.put('8', "八");
        monthMap.put('9', "九");
        monthMap.put('0', "十");

        // 日期特殊映射
        dayMap.put('1', "一");
        dayMap.put('2', "二");
        dayMap.put('3', "三");
        dayMap.put('4', "四");
        dayMap.put('5', "五");
        dayMap.put('6', "六");
        dayMap.put('7', "七");
        dayMap.put('8', "八");
        dayMap.put('9', "九");
        dayMap.put('0', "十");
    }

    public static String convertToChineseDate(Date date) {
        // 格式化日期为年月日部分
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd");

        String yearStr = yearFormat.format(date);
        String monthStr = monthFormat.format(date);
        String dayStr = dayFormat.format(date);

        // 转换年份
        StringBuilder chineseYear = new StringBuilder();
        for (char c : yearStr.toCharArray()) {
            chineseYear.append(digitMap.get(c));
        }
        chineseYear.append("年");

        // 转换月份
        StringBuilder chineseMonth = new StringBuilder();
        if (monthStr.length() == 2) {
            if (monthStr.charAt(0) == '1') {
                chineseMonth.append("十");
            } else if (monthStr.charAt(0) != '0') {
                chineseMonth.append(monthMap.get(monthStr.charAt(0))).append("十");
            }

            if (monthStr.charAt(1) != '0') {
                chineseMonth.append(monthMap.get(monthStr.charAt(1)));
            }
        } else {
            chineseMonth.append(monthMap.get(monthStr.charAt(0)));
        }
        chineseMonth.append("月");

        // 转换日期
        StringBuilder chineseDay = new StringBuilder();
        if (dayStr.length() == 2) {
            if (dayStr.charAt(0) == '1') {
                chineseDay.append("十");
            } else if (dayStr.charAt(0) != '0') {
                chineseDay.append(dayMap.get(dayStr.charAt(0))).append("十");
            }

            if (dayStr.charAt(1) != '0') {
                chineseDay.append(dayMap.get(dayStr.charAt(1)));
            }
        } else {
            chineseDay.append(dayMap.get(dayStr.charAt(0)));
        }
        chineseDay.append("日");

        return chineseYear.toString() + chineseMonth.toString() + chineseDay.toString();
    }

    public static void main(String[] args) {
        Date currentDate = new Date();
        String chineseDate = convertToChineseDate(currentDate);

        System.out.println("当前日期: " + new SimpleDateFormat("yyyy-MM-dd").format(currentDate));
        System.out.println("汉字表示: " + chineseDate);
    }

}
