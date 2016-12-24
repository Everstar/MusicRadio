package com.musicbubble.service.recommend;

/**
 * Created by happyfarmer on 12/25/2016.
 */
public class SongMes {
    private int song_id;

    private double angle_value;

    private double[] song_feaVec;

    public void setAngle_value(double angle_value) {
        this.angle_value = angle_value;
    }

    public void setSong_id(int song_id) {
        this.song_id = song_id;
    }

    public void setSong_feaVec(double[] song_feaVec) {
        this.song_feaVec = song_feaVec;
    }

    public int getSong_id() {
        return this.song_id;
    }

    public double getAngle_value() {
        return this.angle_value;
    }

    public double[] getSong_feaVec() {
        return this.song_feaVec;
    }
}
