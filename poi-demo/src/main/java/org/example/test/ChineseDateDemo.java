package org.example.test;

import org.example.util.ChineseDateUtil;

import java.util.Date;

/**
 * @author: Taoao_
 * @create: 2025-08-20 11:14
 * @desc:
 */
public class ChineseDateDemo {
    public static void main(String[] args) {
        Date date = new Date();
        String s = ChineseDateUtil.convertToChineseDate(date);
        System.out.println("s = " + s);
    }
}
