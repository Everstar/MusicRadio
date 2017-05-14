package com.musicbubble.model;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by happyfarmer on 2017/5/10.
 */
@Entity
@Table(name = "user", schema = "db_03", catalog = "")
public class UserEntity {
    private int userId;
    private String userName;
    private String passwd;
    private Integer imageId;
    private String sex;
    private Integer rank;
    private Integer experience;
    private Integer listId;
    private Timestamp lastSignin;
    private String mail;
    private String birthday;

    @Id
    @Column(name = "user_id", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "user_name", nullable = true, length = 100)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "passwd", nullable = true, length = 128)
    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    @Basic
    @Column(name = "image_id", nullable = true)
    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    @Basic
    @Column(name = "sex", nullable = true, length = 1)
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Basic
    @Column(name = "rank", nullable = true)
    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    @Basic
    @Column(name = "experience", nullable = true)
    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    @Basic
    @Column(name = "list_id", nullable = true)
    public Integer getListId() {
        return listId;
    }

    public void setListId(Integer listId) {
        this.listId = listId;
    }

    @Basic
    @Column(name = "last_signin", nullable = true)
    public Timestamp getLastSignin() {
        return lastSignin;
    }

    public void setLastSignin(Timestamp lastSignin) {
        this.lastSignin = lastSignin;
    }

    @Basic
    @Column(name = "mail", nullable = true, length = 100)
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Basic
    @Column(name = "birthday", nullable = true, length = 100)
    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity entity = (UserEntity) o;

        if (userId != entity.userId) return false;
        if (userName != null ? !userName.equals(entity.userName) : entity.userName != null) return false;
        if (passwd != null ? !passwd.equals(entity.passwd) : entity.passwd != null) return false;
        if (imageId != null ? !imageId.equals(entity.imageId) : entity.imageId != null) return false;
        if (sex != null ? !sex.equals(entity.sex) : entity.sex != null) return false;
        if (rank != null ? !rank.equals(entity.rank) : entity.rank != null) return false;
        if (experience != null ? !experience.equals(entity.experience) : entity.experience != null) return false;
        if (listId != null ? !listId.equals(entity.listId) : entity.listId != null) return false;
        if (lastSignin != null ? !lastSignin.equals(entity.lastSignin) : entity.lastSignin != null) return false;
        if (mail != null ? !mail.equals(entity.mail) : entity.mail != null) return false;
        if (birthday != null ? !birthday.equals(entity.birthday) : entity.birthday != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (passwd != null ? passwd.hashCode() : 0);
        result = 31 * result + (imageId != null ? imageId.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (rank != null ? rank.hashCode() : 0);
        result = 31 * result + (experience != null ? experience.hashCode() : 0);
        result = 31 * result + (listId != null ? listId.hashCode() : 0);
        result = 31 * result + (lastSignin != null ? lastSignin.hashCode() : 0);
        result = 31 * result + (mail != null ? mail.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        return result;
    }
}
