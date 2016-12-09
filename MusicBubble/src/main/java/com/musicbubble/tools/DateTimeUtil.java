package com.musicbubble.tools;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by happyfarmer on 2016/12/9.
 */
public class DateTimeUtil {
    private static String dateFormat = "yyyy-MM-dd HH:mm:ss";

    public static String makeExpireTime(int seconds){
        long currentTime = System.currentTimeMillis();
        currentTime += seconds * 1000;
        Date date = new Date(currentTime);
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        return formatter.format(date);
    }

    public static Date getExpireTime(String timeStr){
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        Date date = formatter.parse(timeStr, new ParsePosition(0));
        return date;
    }

    public static boolean expires(Date date){
        return date.before(new Date());
    }

}
