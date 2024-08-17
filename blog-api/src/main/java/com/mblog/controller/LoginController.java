package com.mblog.controller;

import com.mblog.model.User;
import com.mblog.model.Blog;
import com.mblog.model.Result;
import com.mblog.model.LoginInfo;
import com.mblog.service.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController	// This means that this class is a Controller
public class LoginController {
    @Autowired // This means to get the bean called userRepository
    private UserRepository userRepository;

    @PostMapping(path="/login")
    public Result login (@RequestBody LoginInfo loginInfo) {
        User user = userRepository.findByUsernameAndPassword(loginInfo.getUsername(), loginInfo.getPassword());

        if (!"ROLE_admin".equals(user.getRole())) {
            return Result.create(403, "无权限");
        }
//        Map<String, Object> map = new HashMap<>(4);
//        map.put("user", user);
//        map.put("token", jwt);
//        return Result.ok("添加成功",user);
        return Result.ok("添加成功",user);
    }
}