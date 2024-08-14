package com.mblog.service;

import com.mblog.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
public interface BlogRepository extends JpaRepository<Blog, Integer> {

    @Query(value = "select b from Blog b where b.title like concat('%', :title, '%')")
    List<Blog> findBlogByTitle(@Param("title") String title);

    List<Blog> findAllByTitleContains(String title);

    @Query("select b from Blog b where b.title = :title")
    Optional<Blog> getByTitle(@Param("title") String title);

    @Query(value = "select b.* from blog b where b.title = :title", nativeQuery = true)
    Optional<Blog> getByTitle2(@Param("title") String title);
}

