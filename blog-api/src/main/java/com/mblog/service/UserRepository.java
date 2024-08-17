package com.mblog.service;

import com.mblog.model.Blog;
import com.mblog.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsernameAndPassword(@Param("username")String username,@Param("password") String password);

}

