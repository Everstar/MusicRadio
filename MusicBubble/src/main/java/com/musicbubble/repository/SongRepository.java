package com.musicbubble.repository;

import com.musicbubble.model.SongEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by happyfarmer on 12/17/2016.
 */
public interface SongRepository extends JpaRepository<SongEntity, Integer> {

    @Query("select s.styles from SongEntity s where s.songId between ?1 and ?2")
    List<String> findRangeOfSongById(int pre_id, int las_id);

    @Modifying
    @Transactional
    @Query("update SongEntity s set s.playedTimes = ?2 where s.songId = ?1")
    void updatePlayedTimes(int song_id, int playedTimes);
}