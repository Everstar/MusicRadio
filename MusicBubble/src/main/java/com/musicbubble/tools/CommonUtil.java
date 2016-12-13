package com.musicbubble.tools;

/**
 * Created by happyfarmer on 12/12/2016.
 */
public class CommonUtil {
    public static int[] exp = new int[]{100, 1500, 4500, 10000, 45000, 100000};
    public static int calExp(int level){
        assert level >= 1 && level <= 6;
        return exp[level - 1];
    }
}
