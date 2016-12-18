package com.musicbubble.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by happyfarmer on 12/16/2016.
 */
public class ContainEntityPK implements Serializable {
    private int listId;
    private int songId;

    @Column(name = "list_id", nullable = false)
    @Id
    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    @Column(name = "song_id", nullable = false)
    @Id
    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContainEntityPK that = (ContainEntityPK) o;

        if (listId != that.listId) return false;
        if (songId != that.songId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = listId;
        result = 31 * result + songId;
        return result;
    }
}
