package com.mblog.controller;

import com.mblog.model.SiteConf;
import com.mblog.service.SiteConfRepository;
import com.mblog.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.HashOperations;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import com.mblog.service.Htmlservice;

@RestController // This means that this class is a Controller
@RequestMapping(path = "/")
public class SiteConfController {

  @Autowired private SiteConfRepository siteConfRepository;

  @Autowired private RedisTemplate<String, Object> redisTemplate;

  private final Htmlservice htmlservice;

  @Autowired
  public SiteConfController(Htmlservice service) {
    this.htmlservice = service;
  }

  @GetMapping(path = "/getAbout")
  public Result getAbout() {
    HashOperations<String, String, HashMap> hashOperations = redisTemplate.opsForHash();
    Object value = hashOperations.get("redis-key", "AboutMe");
    if (value != null) {
      return Result.ok("成功", value);
    }
    SiteConf about = siteConfRepository.findByName("AboutMe");
    redisTemplate.opsForHash().put("redis-key", "AboutMe", about);
    redisTemplate.expire("redis-key", 5, TimeUnit.DAYS);

    return Result.ok("成功", about);
  }

  @PostMapping(path = "/updateAbout")
  public Result updateAbout(@RequestBody SiteConf about) {
    SiteConf oldRes = siteConfRepository.findByName("AboutMe");
    if (oldRes != null) {
      about.setId(oldRes.getId());
    }
    about.setName("AboutMe");
    String html = htmlservice.markdownToHtml(about.getContent());
    System.out.println(html);
    about.setHtml(html);
    siteConfRepository.save(about);

    redisTemplate.opsForHash().put("redis-key", "AboutMe", about);
    redisTemplate.expire("redis-key", 24, TimeUnit.HOURS);

    return Result.ok("添加成功");
  }

  // TODO 公共方法
  @GetMapping(path = "/getTodoList")
  public Result getTodoList() {
    HashOperations<String, String, HashMap> hashOperations = redisTemplate.opsForHash();
    Object value = hashOperations.get("redis-key", "TodoList");
    if (value != null) {
      return Result.ok("成功", value);
    }
    SiteConf todolist = siteConfRepository.findByName("TODOLIST");

    redisTemplate.opsForHash().put("redis-key", "TodoList", todolist);
    redisTemplate.expire("redis-key", 5, TimeUnit.DAYS);

    return Result.ok("成功", todolist);
  }

  @PostMapping(path = "/updateTodoList")
  public Result updateTODO(@RequestBody SiteConf todolist) {
    SiteConf oldRes = siteConfRepository.findByName("TODOLIST");
    if (oldRes != null) {
      todolist.setId(oldRes.getId());
    }
    todolist.setName("TODOLIST");
    String html = htmlservice.markdownToHtml(todolist.getContent());
    System.out.println(html);
    todolist.setHtml(html);
    siteConfRepository.save(todolist);

    redisTemplate.opsForHash().put("redis-key", "TodoList", todolist);
    redisTemplate.expire("redis-key", 24, TimeUnit.HOURS);
    return Result.ok("添加成功");
  }
}
