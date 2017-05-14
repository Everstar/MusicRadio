package com.musicbubble.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by happyfarmer on 5/6/2017.
 */
public class MessageEntityPK implements Serializable {
    private int user1Id;
    private int user2Id;

    @Column(name = "user1_id", nullable = false)
    @Id
    public int getUser1Id() {
        return user1Id;
    }

    public void setUser1Id(int user1Id) {
        this.user1Id = user1Id;
    }

    @Column(name = "user2_id", nullable = false)
    @Id
    public int getUser2Id() {
        return user2Id;
    }

    public void setUser2Id(int user2Id) {
        this.user2Id = user2Id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageEntityPK that = (MessageEntityPK) o;

        if (user1Id != that.user1Id) return false;
        if (user2Id != that.user2Id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = user1Id;
        result = 31 * result + user2Id;
        return result;
    }
}
