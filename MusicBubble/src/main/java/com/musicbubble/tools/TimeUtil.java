package com.musicbubble.tools;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by happyfarmer on 2016/12/9.
 */
public class TimeUtil {
    public static final int MomentInterval = 7 * 24 * 3600;

    private static final String dateFormat = "yyyy-MM-dd HH:mm:ss";

    public static String makeExpireTime(int seconds){
        long currentTime = System.currentTimeMillis();
        currentTime += seconds * 1000;
        Date date = new Date(currentTime);
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        return formatter.format(date);
    }

    private static Date getExpireTime(String timeStr){
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        Date date = formatter.parse(timeStr, new ParsePosition(0));
        return date;
    }

    public static boolean expires(String timeStr){
        return getExpireTime(timeStr).before(new Date(System.currentTimeMillis()));
    }

    public static String GetTimeStampString(Timestamp timestamp){
        DateFormat format = new SimpleDateFormat(dateFormat);
        return format.format(timestamp);
    }
}
