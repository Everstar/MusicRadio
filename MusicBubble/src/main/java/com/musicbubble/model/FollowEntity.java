package com.musicbubble.model;

import javax.persistence.*;

/**
 * Created by happyfarmer on 2016/12/3.
 */
@Entity
@Table(name = "Follow", schema = "DB_03", catalog = "")
public class FollowEntity {
    private int fId;
    private Integer userId;
    private Integer followId;

    @Id
    @Column(name = "f_id", nullable = false)
    public int getfId() {
        return fId;
    }

    public void setfId(int fId) {
        this.fId = fId;
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
    @Column(name = "follow_id", nullable = true)
    public Integer getFollowId() {
        return followId;
    }

    public void setFollowId(Integer followId) {
        this.followId = followId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FollowEntity that = (FollowEntity) o;

        if (fId != that.fId) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (followId != null ? !followId.equals(that.followId) : that.followId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = fId;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (followId != null ? followId.hashCode() : 0);
        return result;
    }
}
