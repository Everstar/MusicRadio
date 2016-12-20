package com.musicbubble.repository;

import com.musicbubble.model.ContainEntity;
import com.musicbubble.model.ContainEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by happyfarmer on 12/15/2016.
 */
public interface ContainRepository extends JpaRepository<ContainEntity, ContainEntityPK>{
    @Modifying
    @Query("update ContainEntity c set c.imageId = ?3 where c.songId = ?2 and c.listId = ?1")
    int updateSongImage(int list_id, int song_id, int image_id);

    @Query("select c.imageId from ContainEntity c where c.listId = ?1 and c.songId = ?2")
    int findImageIdOfSong(int list_id, int song_id);

    List<ContainEntity> findByListId(int listId);

    @Modifying
    @Query("delete from ContainEntity c where c.listId = ?1")
    void deleteByListId(int list_id);
}
