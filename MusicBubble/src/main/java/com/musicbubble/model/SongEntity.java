package com.musicbubble.model;

import javax.persistence.*;

/**
 * Created by happyfarmer on 12/17/2016.
 */
@Entity
@Table(name = "song", schema = "db_03", catalog = "")
public class SongEntity {
    private int songId;
    private Integer neteaseId;
    private String songName;
    private String authorName;
    private Integer lastTime;
    private String songUri;
    private String songType;

    @Id
    @Column(name = "song_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    @Basic
    @Column(name = "netease_id", nullable = true)
    public Integer getNeteaseId() {
        return neteaseId;
    }

    public void setNeteaseId(Integer neteaseId) {
        this.neteaseId = neteaseId;
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
    @Column(name = "author_name", nullable = true, length = 100)
    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    @Basic
    @Column(name = "last_time", nullable = true)
    public Integer getLastTime() {
        return lastTime;
    }

    public void setLastTime(Integer lastTime) {
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

        SongEntity entity = (SongEntity) o;

        if (songId != entity.songId) return false;
        if (neteaseId != null ? !neteaseId.equals(entity.neteaseId) : entity.neteaseId != null) return false;
        if (songName != null ? !songName.equals(entity.songName) : entity.songName != null) return false;
        if (authorName != null ? !authorName.equals(entity.authorName) : entity.authorName != null) return false;
        if (lastTime != null ? !lastTime.equals(entity.lastTime) : entity.lastTime != null) return false;
        if (songUri != null ? !songUri.equals(entity.songUri) : entity.songUri != null) return false;
        if (songType != null ? !songType.equals(entity.songType) : entity.songType != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = songId;
        result = 31 * result + (neteaseId != null ? neteaseId.hashCode() : 0);
        result = 31 * result + (songName != null ? songName.hashCode() : 0);
        result = 31 * result + (authorName != null ? authorName.hashCode() : 0);
        result = 31 * result + (lastTime != null ? lastTime.hashCode() : 0);
        result = 31 * result + (songUri != null ? songUri.hashCode() : 0);
        result = 31 * result + (songType != null ? songType.hashCode() : 0);
        return result;
    }
}
