package com.mblog.controller;

import com.mblog.model.Tag;
import com.mblog.model.Result;
import com.mblog.model.PageResult;
import com.mblog.service.AdminTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController	// This means that this class is a Controller
@RequestMapping(path="/") // This means URL's start with /demo (after Application path)
public class AdminTag {
    @Autowired // This means to get the bean called userRepository
    private AdminTagRepository adminTagRepository;

    @GetMapping(path="/getTagAll")
    public @ResponseBody List<Tag> getTagAll() {
        return adminTagRepository.findAll();
    }

    @GetMapping(path="/tags")
    public Result getAllPage(@RequestParam(value = "page",defaultValue = "0") Integer page) {
        Pageable request = PageRequest.of(page, 10);
        Integer count = adminTagRepository.findCount();
        PageResult<Tag> pageResult = new PageResult<>(count, adminTagRepository.findCurPage(request));
        return Result.ok("请求成功", pageResult);
    }

    @PostMapping(path="/addTag")
    public Result addBlog (@RequestBody Tag tag) {
        adminTagRepository.save(tag);
        return Result.ok("添加成功");
    }

    @PostMapping(path="/editTag")
    public Result editTag (@RequestBody Tag tag) {
        adminTagRepository.save(tag);
        return Result.ok("添加成功");
    }

    @PostMapping(path="/deleteTagById")
    public Result deleteTagById (Integer id) {
        adminTagRepository.deleteById(id);
        return Result.ok("删除成功");
    }
}