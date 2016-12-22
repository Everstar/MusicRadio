package com.musicbubble.repository;

import com.musicbubble.model.TastestyleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by happyfarmer on 12/22/2016.
 */
public interface StyleRepository extends JpaRepository<TastestyleEntity, Integer> {

}
