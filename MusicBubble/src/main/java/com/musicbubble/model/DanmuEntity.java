package com.musicbubble.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by happyfarmer on 5/6/2017.
 */
@Entity
@Table(name = "danmu", schema = "db_03", catalog = "")
public class DanmuEntity {
    private int danmuId;
    private Integer userId;
    private String content;
    private Timestamp globalTime;
    private Integer localTime;
    private Integer songId;

    @Id
    @Column(name = "danmu_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getDanmuId() {
        return danmuId;
    }

    public void setDanmuId(int danmuId) {
        this.danmuId = danmuId;
    }

    @Basic
    @Column(name = "user_id", nullable = true)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "content", nullable = true, length = 100)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Basic
    @Column(name = "global_time", nullable = true)
    public Timestamp getGlobalTime() {
        return globalTime;
    }

    public void setGlobalTime(Timestamp globalTime) {
        this.globalTime = globalTime;
    }

    @Basic
    @Column(name = "local_time", nullable = true)
    public Integer getLocalTime() {
        return localTime;
    }

    public void setLocalTime(Integer localTime) {
        this.localTime = localTime;
    }

    @Basic
    @Column(name = "song_id", nullable = true)
    public Integer getSongId() {
        return songId;
    }

    public void setSongId(Integer songId) {
        this.songId = songId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DanmuEntity that = (DanmuEntity) o;

        if (danmuId != that.danmuId) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (globalTime != null ? !globalTime.equals(that.globalTime) : that.globalTime != null) return false;
        if (localTime != null ? !localTime.equals(that.localTime) : that.localTime != null) return false;
        if (songId != null ? !songId.equals(that.songId) : that.songId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = danmuId;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (globalTime != null ? globalTime.hashCode() : 0);
        result = 31 * result + (localTime != null ? localTime.hashCode() : 0);
        result = 31 * result + (songId != null ? songId.hashCode() : 0);
        return result;
    }
}
