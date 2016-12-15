package com.musicbubble.repository;

import com.musicbubble.model.ContainEntity;
import com.musicbubble.model.ContainEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by happyfarmer on 12/15/2016.
 */
public interface ContainRepository extends JpaRepository<ContainEntity, ContainEntityPK>{

}
