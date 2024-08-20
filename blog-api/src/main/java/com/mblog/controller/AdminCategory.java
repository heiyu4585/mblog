package com.mblog.controller;

import com.mblog.model.Category;
import com.mblog.model.Result;
import com.mblog.model.PageResult;
import com.mblog.service.AdminCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController	// This means that this class is a Controller
@RequestMapping(path="/") // This means URL's start with /demo (after Application path)
public class AdminCategory {
    @Autowired // This means to get the bean called userRepository
    private AdminCategoryRepository adminCategoryRepository;

//    @GetMapping(path="/categories")
//    public @ResponseBody Iterable<Category> getCategoryAll() {
//        // This returns a JSON or XML with the users
//        return adminCategoryRepository.findAll();
//    }

    @GetMapping(path="/getCategoryAll")
    public @ResponseBody List<Category> getCategoryAll() {
        return adminCategoryRepository.findAll();
    }

    @GetMapping(path="/categories")
    public Result getCategories(@RequestParam(value = "page",defaultValue = "0") Integer page) {
        Pageable request = PageRequest.of(page, 10);
        Integer count = adminCategoryRepository.findCount();
        PageResult<Category> pageResult = new PageResult<>(count, adminCategoryRepository.findCurPage(request));
        return Result.ok("请求成功", pageResult);
    }

    @PostMapping(path="/addCategory")
    public Result addBlog (@RequestBody Category category) {
        adminCategoryRepository.save(category);
        return Result.ok("添加成功");
    }

    @PostMapping(path="/editCategory")
    public Result editCategory (@RequestBody Category category) {
        adminCategoryRepository.save(category);
        return Result.ok("添加成功");
    }

    @PostMapping(path="/deleteCategoryById")
    public Result deleteCategoryById (Integer id) {
        adminCategoryRepository.deleteById(id);
        return Result.ok("删除成功");
    }
}