package com.musicbubble.repository;

import com.musicbubble.model.SongEntity;
import com.musicbubble.model.SongListEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Created by happyfarmer on 2016/12/9.
 */
@Repository
public interface SongListRepository extends JpaRepository<SongListEntity, Integer> {

    @Query("select s from SongListEntity s order by s.likes desc")
    Page<SongListEntity> findHotlist(Pageable pageable);

    List<SongListEntity> findByUserId(int user_id);

    @Query("select s.listId from SongListEntity s where s.userId=?1")
    List<Integer> findListIdByUserId(int user_id);

    @Query("select s.listId from SongListEntity s where s.userId = ?1 and s.listName='MyFavorite'")
    int findFavoriteSongList(int user_id);

    @Query("select count(*) from SongListEntity s where s.userId = ?1")
    int countList(int user_id);

    @Query("select s from SongListEntity s where s.userId = ?1")
    Page<SongListEntity> findSongListByUserIdAndPage(int user_id, Pageable pageable);

    @Query("select s from SongListEntity s order by s.createTime desc")
    Page<SongListEntity> findNearestSongList(Pageable pageable);

    @Modifying
    @Query("update SongListEntity s set s.listName = ?2 , s.profile = ?3 , s.imageId = ?4 where s.listId = ?1")
    int updateListInfo(int list_id, String name, String desc, int image_id);

    @Query("select s from ContainEntity c inner join SongEntity s where c.songId = s.songId and c.listId = ?1")
    List<SongEntity> findSongsByListId(int list_id);
}