package com.musicbubble.repository;

import com.musicbubble.model.DanmuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by happyfarmer on 5/6/2017.
 */
public interface DanmuRepository extends JpaRepository<DanmuEntity, Integer> {

    @Query("select d from DanmuEntity d where d.songId = ?1")
    List<DanmuEntity> findDanmuBySongId(int song_id);

}
