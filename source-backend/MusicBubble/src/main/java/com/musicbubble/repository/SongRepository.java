package com.musicbubble.repository;

import com.musicbubble.model.SongEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by happyfarmer on 12/17/2016.
 */
public interface SongRepository extends JpaRepository<SongEntity, Integer> {

    SongEntity findByNeteaseId(int neteaseId);

    @Query("select s.styles from SongEntity s where s.songId between ?1 and ?2")
    List<String> findRangeOfSongById(int pre_id, int las_id);

    @Modifying
    @Query("update SongEntity s set s.styles = ?2 where s.songId = ?1")
    void updateStyle(int song_id, String style);
}
