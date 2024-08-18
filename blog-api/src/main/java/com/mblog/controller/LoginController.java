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
@RequestMapping(path="/admin") // This means URL's start with /demo (after Application path)
public class LoginController {
    @Autowired // This means to get the bean called userRepository
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;

    public static final String ADMIN_PREFIX = "admin:";
    @PostMapping(path="/login")
    public Result login (@RequestBody LoginInfo loginInfo)throws IOException, ServletException {

        User user = userRepository.findByUsernameAndPassword(loginInfo.getUsername(), loginInfo.getPassword());

        Map<String, Object> map = new HashMap<>();

        try {
            String jwt = JWTProvider.generateToken(loginInfo.getUsername());
            user.setPassword(null);
            map.put("user", user);
            map.put("token", jwt);
        } catch (BadCredentialsException ex) {
            map.put("error", ex.getMessage());
        }
        return Result.ok("登录成功",map);
    }
}