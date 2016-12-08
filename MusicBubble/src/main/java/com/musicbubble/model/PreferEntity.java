package com.musicbubble.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by happyfarmer on 2016/12/3.
 */
@Entity
@Table(name = "Prefer", schema = "DB_03", catalog = "")
@IdClass(PreferEntityPK.class)
public class PreferEntity {
    private int userId;
    private int listId;
    private Timestamp preferTime;

    @Id
    @Column(name = "user_id", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Id
    @Column(name = "list_id", nullable = false)
    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    @Basic
    @Column(name = "prefer_time", nullable = true)
    public Timestamp getPreferTime() {
        return preferTime;
    }

    public void setPreferTime(Timestamp preferTime) {
        this.preferTime = preferTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PreferEntity that = (PreferEntity) o;

        if (userId != that.userId) return false;
        if (listId != that.listId) return false;
        if (preferTime != null ? !preferTime.equals(that.preferTime) : that.preferTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + listId;
        result = 31 * result + (preferTime != null ? preferTime.hashCode() : 0);
        return result;
    }
}
