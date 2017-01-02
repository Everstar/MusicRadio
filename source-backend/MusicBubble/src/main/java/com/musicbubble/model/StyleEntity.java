package com.musicbubble.model;

import javax.persistence.*;

/**
 * Created by happyfarmer on 12/20/2016.
 */
@Entity
@Table(name = "style", schema = "db_03", catalog = "")
public class StyleEntity {
    private int styleId;
    private String styleName;

    @Id
    @Column(name = "style_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getStyleId() {
        return styleId;
    }

    public void setStyleId(int styleId) {
        this.styleId = styleId;
    }

    @Basic
    @Column(name = "style_name", nullable = true, length = 45)
    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StyleEntity that = (StyleEntity) o;

        if (styleId != that.styleId) return false;
        if (styleName != null ? !styleName.equals(that.styleName) : that.styleName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = styleId;
        result = 31 * result + (styleName != null ? styleName.hashCode() : 0);
        return result;
    }
}
