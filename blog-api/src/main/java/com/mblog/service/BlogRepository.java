package com.mblog.service;

import com.mblog.model.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BlogRepository extends JpaRepository<Blog, Integer> {

    @Query(value = "select b from Blog b where b.title like concat('%', :title, '%')")
    List<Blog> findBlogByTitle(@Param("title") String title);

    List<Blog> findAllByTitleContains(String title);

    @Query("select b from Blog b where b.title = :title")
    Optional<Blog> getByTitle(@Param("title") String title);

    @Query(value = "select b.* from blog b where b.title = :title", nativeQuery = true)
    Optional<Blog> getByTitle2(@Param("title") String title);

    @Query("SELECT u FROM User u")
    List<Blog> findBlogs( Pageable pageable);

    @Query(value = "select * from Blog",nativeQuery = true)
    List <Blog> findCurPage( Pageable pageable);

    @Query(value = "select count(*) from Blog",nativeQuery = true)
    Integer findCount();
}

