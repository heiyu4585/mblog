package com.mblog.controller;

import com.mblog.model.User;
import com.mblog.model.Blog;
import com.mblog.model.Result;
import com.mblog.model.LoginInfo;
import com.mblog.service.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;
import com.mblog.config.JWTProvider;
import java.io.IOException;
import jakarta.servlet.ServletException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController	// This means that this class is a Controller
@RequestMapping(path="/") // This means URL's start with /demo (after Application path)
public class LoginController {
    @Autowired // This means to get the bean called userRepository
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;

    public static final String ADMIN_PREFIX = "admin:";
    @PostMapping(path="/login")
    public Result login (@RequestBody LoginInfo loginInfo)throws IOException, ServletException {
        Map<String, Object> map = new HashMap<>();
        try {
            User user = userRepository.findByUsernameAndPassword(loginInfo.getUsername(), loginInfo.getPassword());
            String jwt = JWTProvider.generateToken(loginInfo.getUsername());
            if(null != user){
                user.setPassword(null);
                map.put("user", user);
                map.put("token", jwt);
                return Result.ok("登录成功",map);
            }
        } catch (BadCredentialsException ex) {
            return Result.error(ex.getMessage());
        }
        return Result.error("登录失败");
    }

    @PostMapping("/account")
    public Result account(@RequestBody User user, @RequestHeader(value = "Authorization", defaultValue = "") String jwt) {
        String username = JWTProvider.getTokenBody(jwt).getSubject();

       if(!username.equals( user.getUsername())){
           return Result.error("修改失败");
       }
        User curUser = userRepository.findUserByUsername(user.getUsername());
        curUser.setPassword(user.getPassword());
        userRepository.save(curUser);

        return Result.ok("修改成功");
    }
}