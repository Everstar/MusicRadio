package com.musicbubble.model;

import javax.persistence.*;

/**
 * Created by happyfarmer on 12/16/2016.
 */
@Entity
@Table(name = "contain", schema = "db_03", catalog = "")
@IdClass(ContainEntityPK.class)
public class ContainEntity {
    private int listId;
    private int songId;
    private Integer imageId;

    @Id
    @Column(name = "list_id", nullable = false)
    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    @Id
    @Column(name = "song_id", nullable = false)
    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    @Basic
    @Column(name = "image_id", nullable = true)
    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContainEntity that = (ContainEntity) o;

        if (listId != that.listId) return false;
        if (songId != that.songId) return false;
        if (imageId != null ? !imageId.equals(that.imageId) : that.imageId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = listId;
        result = 31 * result + songId;
        result = 31 * result + (imageId != null ? imageId.hashCode() : 0);
        return result;
    }
}
