package com.musicbubble.repository;

import com.musicbubble.model.SongListEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by happyfarmer on 2016/12/9.
 */
@Repository
public interface SongListRepository extends JpaRepository<SongListEntity, Integer>{

    @Query("select s from SongListEntity s order by s.likes desc")
    Page<SongListEntity> findHotlist(Pageable pageable);

    @Query("select s from SongListEntity s where s.userId = ?1")
    List<SongListEntity> findByUserId(int user_id);
}
