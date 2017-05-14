package com.musicbubble.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by happyfarmer on 2017/5/10.
 */
@Entity
@Table(name = "song", schema = "db_03", catalog = "")
public class SongEntity {
    private int songId;
    private String songName;
    private String authorName;
    private Integer lastTime;
    private String songUri;
    private String language;
    private String styles;
    private Integer uploaderId;
    private Timestamp uploadTime;
    private Integer playedTimes;

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
    @Column(name = "language", nullable = true, length = 45)
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Basic
    @Column(name = "styles", nullable = true, length = 45)
    public String getStyles() {
        return styles;
    }

    public void setStyles(String styles) {
        this.styles = styles;
    }

    @Basic
    @Column(name = "uploader_id", nullable = true)
    public Integer getUploaderId() {
        return uploaderId;
    }

    public void setUploaderId(Integer uploaderId) {
        this.uploaderId = uploaderId;
    }

    @Basic
    @Column(name = "upload_time", nullable = true)
    public Timestamp getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Timestamp uploadTime) {
        this.uploadTime = uploadTime;
    }

    @Basic
    @Column(name = "played_times", nullable = true)
    public Integer getPlayedTimes() {
        return playedTimes;
    }

    public void setPlayedTimes(Integer playedTimes) {
        this.playedTimes = playedTimes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SongEntity entity = (SongEntity) o;

        if (songId != entity.songId) return false;
        if (songName != null ? !songName.equals(entity.songName) : entity.songName != null) return false;
        if (authorName != null ? !authorName.equals(entity.authorName) : entity.authorName != null) return false;
        if (lastTime != null ? !lastTime.equals(entity.lastTime) : entity.lastTime != null) return false;
        if (songUri != null ? !songUri.equals(entity.songUri) : entity.songUri != null) return false;
        if (language != null ? !language.equals(entity.language) : entity.language != null) return false;
        if (styles != null ? !styles.equals(entity.styles) : entity.styles != null) return false;
        if (uploaderId != null ? !uploaderId.equals(entity.uploaderId) : entity.uploaderId != null) return false;
        if (uploadTime != null ? !uploadTime.equals(entity.uploadTime) : entity.uploadTime != null) return false;
        if (playedTimes != null ? !playedTimes.equals(entity.playedTimes) : entity.playedTimes != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = songId;
        result = 31 * result + (songName != null ? songName.hashCode() : 0);
        result = 31 * result + (authorName != null ? authorName.hashCode() : 0);
        result = 31 * result + (lastTime != null ? lastTime.hashCode() : 0);
        result = 31 * result + (songUri != null ? songUri.hashCode() : 0);
        result = 31 * result + (language != null ? language.hashCode() : 0);
        result = 31 * result + (styles != null ? styles.hashCode() : 0);
        result = 31 * result + (uploaderId != null ? uploaderId.hashCode() : 0);
        result = 31 * result + (uploadTime != null ? uploadTime.hashCode() : 0);
        result = 31 * result + (playedTimes != null ? playedTimes.hashCode() : 0);
        return result;
    }
}
