package com.musicbubble.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by happyfarmer on 2016/12/3.
 */
@Entity
@Table(name = "Song", schema = "DB_03", catalog = "")
public class SongEntity {
    private int songId;
    private String songName;
    private String authorName;
    private Timestamp lastTime;
    private String songUri;
    private String songType;

    @Id
    @Column(name = "song_id", nullable = false)
    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    @Basic
    @Column(name = "song_name", nullable = false, length = 100)
    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    @Basic
    @Column(name = "author_name", nullable = false, length = 100)
    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    @Basic
    @Column(name = "last_time", nullable = true)
    public Timestamp getLastTime() {
        return lastTime;
    }

    public void setLastTime(Timestamp lastTime) {
        this.lastTime = lastTime;
    }

    @Basic
    @Column(name = "song_uri", nullable = false, length = 255)
    public String getSongUri() {
        return songUri;
    }

    public void setSongUri(String songUri) {
        this.songUri = songUri;
    }

    @Basic
    @Column(name = "song_type", nullable = true, length = 1)
    public String getSongType() {
        return songType;
    }

    public void setSongType(String songType) {
        this.songType = songType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SongEntity that = (SongEntity) o;

        if (songId != that.songId) return false;
        if (songName != null ? !songName.equals(that.songName) : that.songName != null) return false;
        if (authorName != null ? !authorName.equals(that.authorName) : that.authorName != null) return false;
        if (lastTime != null ? !lastTime.equals(that.lastTime) : that.lastTime != null) return false;
        if (songUri != null ? !songUri.equals(that.songUri) : that.songUri != null) return false;
        if (songType != null ? !songType.equals(that.songType) : that.songType != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = songId;
        result = 31 * result + (songName != null ? songName.hashCode() : 0);
        result = 31 * result + (authorName != null ? authorName.hashCode() : 0);
        result = 31 * result + (lastTime != null ? lastTime.hashCode() : 0);
        result = 31 * result + (songUri != null ? songUri.hashCode() : 0);
        result = 31 * result + (songType != null ? songType.hashCode() : 0);
        return result;
    }
}
