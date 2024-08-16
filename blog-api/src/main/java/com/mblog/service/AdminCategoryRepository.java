package com.mblog.service;

import com.mblog.model.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdminCategoryRepository extends JpaRepository<Category, Integer> {

    @Query(value = "select * from Category",nativeQuery = true)
    List <Category> findCurPage( Pageable pageable);

    @Query(value = "select count(*) from Category",nativeQuery = true)
    Integer findCount();
}

