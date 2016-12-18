package com.musicbubble.repository;

import com.musicbubble.model.SongEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by happyfarmer on 12/17/2016.
 */
public interface SongRepository extends JpaRepository<SongEntity, Integer> {

    SongEntity findByNeteaseId(int neteaseId);
}
