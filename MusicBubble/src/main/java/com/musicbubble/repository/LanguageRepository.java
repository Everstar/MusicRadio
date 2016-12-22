package com.musicbubble.repository;

import com.musicbubble.model.TastelanguageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by happyfarmer on 12/22/2016.
 */
public interface LanguageRepository extends JpaRepository<TastelanguageEntity, Integer> {

    @Modifying
    @Query("update TastelanguageEntity lan set lan.lang1 = lan.lang1 + ?1, lan.lang2 = lan.lang2 + ?2, " +
            "lan.lang3 = lan.lang3 + ?3, lan.lang4 = lan.lang4 + ?4, lan.lang5 = lan.lang5 + ?5 where lan.userId = ?6")
    void update(int lan1, int lan2, int lan3, int lan4, int lan5, int user_id);

}
