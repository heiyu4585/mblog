package com.mblog.service;

import com.mblog.model.Blog;
import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface BlogsRepository extends CrudRepository<Blog, Integer> {

}

