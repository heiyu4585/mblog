package com.mblog.controller;

import com.mblog.model.Blog;
import com.mblog.model.Category;
import com.mblog.model.Tag;
import com.mblog.model.Result;
import com.mblog.model.PageResult;
import com.mblog.service.BlogRepository;
import com.mblog.controller.AdminCategory;
import com.mblog.controller.AdminTag;
import com.mblog.service.Htmlservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.util.Optional;
import java.text.SimpleDateFormat;

@RestController // This means that this class is a Controller
public class BlogController {
  @Autowired // This means to get the bean called userRepository
  // Which is auto-generated by Spring, we will use it to handle the data
  private BlogRepository blogRepository;

  @Autowired private AdminCategory adminCategory;

  @Autowired private AdminTag adminTag;

  private final Htmlservice htmlservice;

  @Autowired
  public BlogController(Htmlservice service) {
    this.htmlservice = service;
  }

  @GetMapping(path = "/getBlog")
  public Result findBlog(@RequestParam(value = "id", defaultValue = "0") Integer id) {
    Optional<Blog> blog = blogRepository.findById(id);
    return Result.ok("请求成功", blog);
  }

  @GetMapping(path = "/getBlogByTitle")
  public @ResponseBody Blog getBlog(@RequestParam(value = "title") String title) {
    return blogRepository.getByTitle(title).orElse(null);
  }

  @GetMapping(path = "/getSite")
  public @ResponseBody Result getSite() {
    Map<String, Object> map = new HashMap<>();
    List<Category> categoryList = adminCategory.getCategoryAll();
    List<Tag> tagList = adminTag.getTagAll();
    map.put("categoryList", categoryList);
    map.put("tagList", tagList);
    map.put("randomBlogList", blogRepository.findRandBLogs());

    return Result.ok("请求成功", map);
  }

  @GetMapping(path = "/all")
  public @ResponseBody Iterable<Blog> getAll() {
    return blogRepository.findAll();
  }

  @GetMapping(path = "/blogs")
  public Result getAllPage(@RequestParam(value = "page") Integer page) {
    Map<String, Object> map = new HashMap<>();
    //    JedisPool pool = new JedisPool("localhost", 6379);
    //    try (Jedis jedis = pool.getResource()) {
    // 将Map转换为JSON字符串
    //      ObjectMapper objectMapper = new ObjectMapper();
    //      String res = jedis.get("blogs:" + page);
    //      System.out.println(res);
    //      if (res != null) {
    //        // 将 JSON 字符串转换为 Map 对象
    //        try {
    //          map = objectMapper.readValue(res, Map.class);
    //        } catch (Exception e) {
    //          e.printStackTrace();
    //        }
    //        return Result.ok("请求成功", map);
    //      }

    Pageable request = PageRequest.of(page - 1, 10);
    Integer totalPage = (int) Math.ceil(blogRepository.findCount() / 10);
    PageResult<Blog> pageResult = new PageResult<>(totalPage, blogRepository.findCurPage(request));
    //      try {
    //        String jsonstring = objectMapper.writeValueAsString(pageResult);
    //        // timeout.unit - must not be null
    //        jedis.set("blogs:" + page, jsonstring);
    //        jedis.expire("myKey", 6000);
    //      } catch (JsonProcessingException e) {
    //        e.printStackTrace();
    //      }
    return Result.ok("请求成功", pageResult);
    //    }
  }

  @GetMapping(path = "/getBlogsByCategoryName")
  public Result getBlogsByCategoryName(
      @RequestParam(value = "page", defaultValue = "0") Integer page, String name) {
    Pageable request = PageRequest.of(page - 1, 10);
    Integer totalPage = (int) Math.ceil(blogRepository.getBlogsTotalByCategoryName(name) / 10);

    PageResult<Blog> pageResult =
        new PageResult<>(totalPage, blogRepository.getBlogsByCategoryName(name, request));
    return Result.ok("请求成功", pageResult);
  }

  @PostMapping(path = "/addBlog")
  public Result addBlog(@RequestBody Blog blog) {
    // 获取当前时间
    Date currentDate = new Date();
    //		/ 创建SimpleDateFormat对象，并指定格式
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    // 使用SimpleDateFormat将日期转换为字符串
    String formattedDateStr = sdf.format(currentDate);
    // 将格式化后的字符串解析为Date对象
    Date formattedDate = null;
    try {
      formattedDate = sdf.parse(formattedDateStr);
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (blog.getCreateTime() == null) {
      blog.setCreateTime(formattedDate);
    }
    blog.setUpdateTime(formattedDate);
    String html = htmlservice.markdownToHtml(blog.getContent());
    System.out.println(html);
    blog.setHtml(html);

    blogRepository.save(blog);
    return Result.ok("添加成功");
  }

  @PostMapping(path = "/deleteBlogById")
  public Result delById(Integer id) {
    blogRepository.deleteById(id);
    return Result.ok("删除成功");
  }

  @ResponseBody
  @GetMapping("searchBlog")
  public Result search(String title) {
    return Result.ok("", blogRepository.findBlogByTitle(title));
  }
}
