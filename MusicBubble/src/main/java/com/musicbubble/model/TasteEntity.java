package com.musicbubble.model;

import javax.persistence.*;

/**
 * Created by happyfarmer on 5/6/2017.
 */
@Entity
@Table(name = "taste", schema = "db_03", catalog = "")
public class TasteEntity {
    private int userId;
    private String language;
    private String style;

    @Id
    @Column(name = "user_id", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "language", nullable = true, length = -1)
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Basic
    @Column(name = "style", nullable = true, length = -1)
    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TasteEntity that = (TasteEntity) o;

        if (userId != that.userId) return false;
        if (language != null ? !language.equals(that.language) : that.language != null) return false;
        if (style != null ? !style.equals(that.style) : that.style != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (language != null ? language.hashCode() : 0);
        result = 31 * result + (style != null ? style.hashCode() : 0);
        return result;
    }
}
