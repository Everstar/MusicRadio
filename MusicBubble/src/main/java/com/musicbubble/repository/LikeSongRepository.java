package com.musicbubble.repository;

import com.musicbubble.model.SongListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by DELL on 2016/12/18.
 */
public interface LikeSongRepository extends JpaRepository<SongListEntity,Integer> {


}
