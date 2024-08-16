package com.mblog.service;

import com.mblog.model.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdminTagRepository extends JpaRepository<Tag, Integer> {
    @Query(value = "select * from Tag",nativeQuery = true)
    List <Tag> findCurPage( Pageable pageable);

    @Query(value = "select count(*) from Tag",nativeQuery = true)
    Integer findCount();
}

