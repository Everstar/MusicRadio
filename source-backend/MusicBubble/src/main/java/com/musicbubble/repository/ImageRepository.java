package com.musicbubble.repository;

import com.musicbubble.model.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by happyfarmer on 2016/12/9.
 */
@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Integer> {

}
