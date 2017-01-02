package com.musicbubble.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by happyfarmer on 12/16/2016.
 */
public class PreferEntityPK implements Serializable {
    private int userId;
    private int listId;

    @Column(name = "user_id", nullable = false)
    @Id
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Column(name = "list_id", nullable = false)
    @Id
    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PreferEntityPK that = (PreferEntityPK) o;

        if (userId != that.userId) return false;
        if (listId != that.listId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + listId;
        return result;
    }
}
