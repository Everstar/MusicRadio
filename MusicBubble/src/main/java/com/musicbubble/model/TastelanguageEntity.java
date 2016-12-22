package com.musicbubble.model;

import javax.persistence.*;

/**
 * Created by happyfarmer on 12/22/2016.
 */
@Entity
@Table(name = "tastelanguage", schema = "db_03", catalog = "")
public class TastelanguageEntity {
    private int userId;
    private Integer lang1;
    private Integer lang2;
    private Integer lang3;
    private Integer lang4;
    private Integer lang5;

    @Id
    @Column(name = "user_id", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "lang1", nullable = true)
    public Integer getLang1() {
        return lang1;
    }

    public void setLang1(Integer lang1) {
        this.lang1 = lang1;
    }

    @Basic
    @Column(name = "lang2", nullable = true)
    public Integer getLang2() {
        return lang2;
    }

    public void setLang2(Integer lang2) {
        this.lang2 = lang2;
    }

    @Basic
    @Column(name = "lang3", nullable = true)
    public Integer getLang3() {
        return lang3;
    }

    public void setLang3(Integer lang3) {
        this.lang3 = lang3;
    }

    @Basic
    @Column(name = "lang4", nullable = true)
    public Integer getLang4() {
        return lang4;
    }

    public void setLang4(Integer lang4) {
        this.lang4 = lang4;
    }

    @Basic
    @Column(name = "lang5", nullable = true)
    public Integer getLang5() {
        return lang5;
    }

    public void setLang5(Integer lang5) {
        this.lang5 = lang5;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TastelanguageEntity that = (TastelanguageEntity) o;

        if (userId != that.userId) return false;
        if (lang1 != null ? !lang1.equals(that.lang1) : that.lang1 != null) return false;
        if (lang2 != null ? !lang2.equals(that.lang2) : that.lang2 != null) return false;
        if (lang3 != null ? !lang3.equals(that.lang3) : that.lang3 != null) return false;
        if (lang4 != null ? !lang4.equals(that.lang4) : that.lang4 != null) return false;
        if (lang5 != null ? !lang5.equals(that.lang5) : that.lang5 != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (lang1 != null ? lang1.hashCode() : 0);
        result = 31 * result + (lang2 != null ? lang2.hashCode() : 0);
        result = 31 * result + (lang3 != null ? lang3.hashCode() : 0);
        result = 31 * result + (lang4 != null ? lang4.hashCode() : 0);
        result = 31 * result + (lang5 != null ? lang5.hashCode() : 0);
        return result;
    }
}
