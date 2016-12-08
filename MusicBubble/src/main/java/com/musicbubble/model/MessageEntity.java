package com.musicbubble.model;

import javax.persistence.*;

/**
 * Created by happyfarmer on 2016/12/3.
 */
@Entity
@Table(name = "Message", schema = "DB_03", catalog = "")
@IdClass(MessageEntityPK.class)
public class MessageEntity {
    private int user1Id;
    private int user2Id;
    private String messageUri;

    @Id
    @Column(name = "user1_id", nullable = false)
    public int getUser1Id() {
        return user1Id;
    }

    public void setUser1Id(int user1Id) {
        this.user1Id = user1Id;
    }

    @Id
    @Column(name = "user2_id", nullable = false)
    public int getUser2Id() {
        return user2Id;
    }

    public void setUser2Id(int user2Id) {
        this.user2Id = user2Id;
    }

    @Basic
    @Column(name = "message_uri", nullable = true, length = 255)
    public String getMessageUri() {
        return messageUri;
    }

    public void setMessageUri(String messageUri) {
        this.messageUri = messageUri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageEntity that = (MessageEntity) o;

        if (user1Id != that.user1Id) return false;
        if (user2Id != that.user2Id) return false;
        if (messageUri != null ? !messageUri.equals(that.messageUri) : that.messageUri != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = user1Id;
        result = 31 * result + user2Id;
        result = 31 * result + (messageUri != null ? messageUri.hashCode() : 0);
        return result;
    }
}
