package com.musicbubble.repository;

import com.musicbubble.model.PreferEntity;

import org.springframework.data.jpa.repository.JpaRepository;


import com.musicbubble.model.PreferEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by happyfarmer on 12/14/2016.
 */
@Service
public interface PreferRepository extends JpaRepository<PreferEntity, PreferEntityPK>{

    @Query("select p from PreferEntity p where p.userId = ?1 and p.preferTime > ?2")
    List<PreferEntity> findPreferByUserIdAndTime(int user_id, Timestamp timestamp);




}
