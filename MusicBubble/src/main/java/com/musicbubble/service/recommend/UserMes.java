package com.musicbubble.service.recommend;

/**
 * Created by happyfarmer on 12/25/2016.
 */
public class UserMes {
    private int user_id;

    private double distance_value;

    private double[] user_feaVec;

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setDistance_value(double distance_value) {
        this.distance_value = distance_value;
    }

    public void setUser_feaVec(double[] user_feaVec) {
        this.user_feaVec = user_feaVec;
    }

    public int getUser_id() {
        return this.user_id;
    }

    public double getDistance_value() {
        return this.distance_value;
    }

    public double[] getUser_feaVec() {
        return this.user_feaVec;
    }
}
