package com.musicbubble.model;

import javax.persistence.*;

/**
 * Created by happyfarmer on 12/16/2016.
 */
@Entity
@Table(name = "image", schema = "db_03", catalog = "")
public class ImageEntity {
    private int imageId;
    private String imageType;
    private String imageUri;

    @Id
    @Column(name = "image_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    @Basic
    @Column(name = "image_type", nullable = true, length = 1)
    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    @Basic
    @Column(name = "image_uri", nullable = true, length = 255)
    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ImageEntity that = (ImageEntity) o;

        if (imageId != that.imageId) return false;
        if (imageType != null ? !imageType.equals(that.imageType) : that.imageType != null) return false;
        if (imageUri != null ? !imageUri.equals(that.imageUri) : that.imageUri != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = imageId;
        result = 31 * result + (imageType != null ? imageType.hashCode() : 0);
        result = 31 * result + (imageUri != null ? imageUri.hashCode() : 0);
        return result;
    }
}
