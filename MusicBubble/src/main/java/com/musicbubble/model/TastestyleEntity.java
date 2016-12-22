package com.musicbubble.model;

import javax.persistence.*;

/**
 * Created by happyfarmer on 12/22/2016.
 */
@Entity
@Table(name = "tastestyle", schema = "db_03", catalog = "")
public class TastestyleEntity {
    private int userId;
    private Integer taste1;
    private Integer taste2;
    private Integer taste3;
    private Integer taste4;
    private Integer taste5;
    private Integer taste6;
    private Integer taste7;
    private Integer taste8;
    private Integer taste9;
    private Integer taste10;
    private Integer taste11;
    private Integer taste12;
    private Integer taste13;
    private Integer taste14;
    private Integer taste15;
    private Integer taste16;
    private Integer taste17;
    private Integer taste18;
    private Integer taste19;
    private Integer taste20;
    private Integer taste21;

    @Id
    @Column(name = "user_id", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "taste1", nullable = true)
    public Integer getTaste1() {
        return taste1;
    }

    public void setTaste1(Integer taste1) {
        this.taste1 = taste1;
    }

    @Basic
    @Column(name = "taste2", nullable = true)
    public Integer getTaste2() {
        return taste2;
    }

    public void setTaste2(Integer taste2) {
        this.taste2 = taste2;
    }

    @Basic
    @Column(name = "taste3", nullable = true)
    public Integer getTaste3() {
        return taste3;
    }

    public void setTaste3(Integer taste3) {
        this.taste3 = taste3;
    }

    @Basic
    @Column(name = "taste4", nullable = true)
    public Integer getTaste4() {
        return taste4;
    }

    public void setTaste4(Integer taste4) {
        this.taste4 = taste4;
    }

    @Basic
    @Column(name = "taste5", nullable = true)
    public Integer getTaste5() {
        return taste5;
    }

    public void setTaste5(Integer taste5) {
        this.taste5 = taste5;
    }

    @Basic
    @Column(name = "taste6", nullable = true)
    public Integer getTaste6() {
        return taste6;
    }

    public void setTaste6(Integer taste6) {
        this.taste6 = taste6;
    }

    @Basic
    @Column(name = "taste7", nullable = true)
    public Integer getTaste7() {
        return taste7;
    }

    public void setTaste7(Integer taste7) {
        this.taste7 = taste7;
    }

    @Basic
    @Column(name = "taste8", nullable = true)
    public Integer getTaste8() {
        return taste8;
    }

    public void setTaste8(Integer taste8) {
        this.taste8 = taste8;
    }

    @Basic
    @Column(name = "taste9", nullable = true)
    public Integer getTaste9() {
        return taste9;
    }

    public void setTaste9(Integer taste9) {
        this.taste9 = taste9;
    }

    @Basic
    @Column(name = "taste10", nullable = true)
    public Integer getTaste10() {
        return taste10;
    }

    public void setTaste10(Integer taste10) {
        this.taste10 = taste10;
    }

    @Basic
    @Column(name = "taste11", nullable = true)
    public Integer getTaste11() {
        return taste11;
    }

    public void setTaste11(Integer taste11) {
        this.taste11 = taste11;
    }

    @Basic
    @Column(name = "taste12", nullable = true)
    public Integer getTaste12() {
        return taste12;
    }

    public void setTaste12(Integer taste12) {
        this.taste12 = taste12;
    }

    @Basic
    @Column(name = "taste13", nullable = true)
    public Integer getTaste13() {
        return taste13;
    }

    public void setTaste13(Integer taste13) {
        this.taste13 = taste13;
    }

    @Basic
    @Column(name = "taste14", nullable = true)
    public Integer getTaste14() {
        return taste14;
    }

    public void setTaste14(Integer taste14) {
        this.taste14 = taste14;
    }

    @Basic
    @Column(name = "taste15", nullable = true)
    public Integer getTaste15() {
        return taste15;
    }

    public void setTaste15(Integer taste15) {
        this.taste15 = taste15;
    }

    @Basic
    @Column(name = "taste16", nullable = true)
    public Integer getTaste16() {
        return taste16;
    }

    public void setTaste16(Integer taste16) {
        this.taste16 = taste16;
    }

    @Basic
    @Column(name = "taste17", nullable = true)
    public Integer getTaste17() {
        return taste17;
    }

    public void setTaste17(Integer taste17) {
        this.taste17 = taste17;
    }

    @Basic
    @Column(name = "taste18", nullable = true)
    public Integer getTaste18() {
        return taste18;
    }

    public void setTaste18(Integer taste18) {
        this.taste18 = taste18;
    }

    @Basic
    @Column(name = "taste19", nullable = true)
    public Integer getTaste19() {
        return taste19;
    }

    public void setTaste19(Integer taste19) {
        this.taste19 = taste19;
    }

    @Basic
    @Column(name = "taste20", nullable = true)
    public Integer getTaste20() {
        return taste20;
    }

    public void setTaste20(Integer taste20) {
        this.taste20 = taste20;
    }

    @Basic
    @Column(name = "taste21", nullable = true)
    public Integer getTaste21() {
        return taste21;
    }

    public void setTaste21(Integer taste21) {
        this.taste21 = taste21;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TastestyleEntity that = (TastestyleEntity) o;

        if (userId != that.userId) return false;
        if (taste1 != null ? !taste1.equals(that.taste1) : that.taste1 != null) return false;
        if (taste2 != null ? !taste2.equals(that.taste2) : that.taste2 != null) return false;
        if (taste3 != null ? !taste3.equals(that.taste3) : that.taste3 != null) return false;
        if (taste4 != null ? !taste4.equals(that.taste4) : that.taste4 != null) return false;
        if (taste5 != null ? !taste5.equals(that.taste5) : that.taste5 != null) return false;
        if (taste6 != null ? !taste6.equals(that.taste6) : that.taste6 != null) return false;
        if (taste7 != null ? !taste7.equals(that.taste7) : that.taste7 != null) return false;
        if (taste8 != null ? !taste8.equals(that.taste8) : that.taste8 != null) return false;
        if (taste9 != null ? !taste9.equals(that.taste9) : that.taste9 != null) return false;
        if (taste10 != null ? !taste10.equals(that.taste10) : that.taste10 != null) return false;
        if (taste11 != null ? !taste11.equals(that.taste11) : that.taste11 != null) return false;
        if (taste12 != null ? !taste12.equals(that.taste12) : that.taste12 != null) return false;
        if (taste13 != null ? !taste13.equals(that.taste13) : that.taste13 != null) return false;
        if (taste14 != null ? !taste14.equals(that.taste14) : that.taste14 != null) return false;
        if (taste15 != null ? !taste15.equals(that.taste15) : that.taste15 != null) return false;
        if (taste16 != null ? !taste16.equals(that.taste16) : that.taste16 != null) return false;
        if (taste17 != null ? !taste17.equals(that.taste17) : that.taste17 != null) return false;
        if (taste18 != null ? !taste18.equals(that.taste18) : that.taste18 != null) return false;
        if (taste19 != null ? !taste19.equals(that.taste19) : that.taste19 != null) return false;
        if (taste20 != null ? !taste20.equals(that.taste20) : that.taste20 != null) return false;
        if (taste21 != null ? !taste21.equals(that.taste21) : that.taste21 != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (taste1 != null ? taste1.hashCode() : 0);
        result = 31 * result + (taste2 != null ? taste2.hashCode() : 0);
        result = 31 * result + (taste3 != null ? taste3.hashCode() : 0);
        result = 31 * result + (taste4 != null ? taste4.hashCode() : 0);
        result = 31 * result + (taste5 != null ? taste5.hashCode() : 0);
        result = 31 * result + (taste6 != null ? taste6.hashCode() : 0);
        result = 31 * result + (taste7 != null ? taste7.hashCode() : 0);
        result = 31 * result + (taste8 != null ? taste8.hashCode() : 0);
        result = 31 * result + (taste9 != null ? taste9.hashCode() : 0);
        result = 31 * result + (taste10 != null ? taste10.hashCode() : 0);
        result = 31 * result + (taste11 != null ? taste11.hashCode() : 0);
        result = 31 * result + (taste12 != null ? taste12.hashCode() : 0);
        result = 31 * result + (taste13 != null ? taste13.hashCode() : 0);
        result = 31 * result + (taste14 != null ? taste14.hashCode() : 0);
        result = 31 * result + (taste15 != null ? taste15.hashCode() : 0);
        result = 31 * result + (taste16 != null ? taste16.hashCode() : 0);
        result = 31 * result + (taste17 != null ? taste17.hashCode() : 0);
        result = 31 * result + (taste18 != null ? taste18.hashCode() : 0);
        result = 31 * result + (taste19 != null ? taste19.hashCode() : 0);
        result = 31 * result + (taste20 != null ? taste20.hashCode() : 0);
        result = 31 * result + (taste21 != null ? taste21.hashCode() : 0);
        return result;
    }
}
