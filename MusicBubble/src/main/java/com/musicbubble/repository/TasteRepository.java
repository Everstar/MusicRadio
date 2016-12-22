package com.musicbubble.repository;

import com.musicbubble.model.TasteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by happyfarmer on 12/22/2016.
 */
public interface TasteRepository extends JpaRepository<TasteEntity, Integer>{

    @Modifying
    @Query("update TasteEntity t set t.language = ?2, t.style = ?3 where t.userId = ?1")
    void update(int user_id, String language, String style);
}
