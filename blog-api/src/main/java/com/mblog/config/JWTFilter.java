package com.mblog.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mblog.config.JWTProvider;
import com.mblog.model.Result;

import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Component
public class JWTFilter extends OncePerRequestFilter {

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        //后台管理路径外的请求直接跳过
//        if (!request.getRequestURI().startsWith(request.getContextPath() + "/admin")) {
//            filterChain.doFilter(request, servletResponse);
//            return;
//        }
        // 这部分出错后，直接返回401，不再走后面的filter
        //后台管理路径外的请求直接跳过
//        if (!request.getRequestURI().startsWith(request.getContextPath() + "/admin")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
        String jwt = request.getHeader("Authorization");
        if (JWTProvider.judgeTokenIsExist(jwt) && JWTProvider.validateToken(jwt)) {
            try {
//                Claims claims = JWTProvider.getTokenBody(jwt);
//                String username = claims.getSubject();
//                List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList((String) claims.get("authorities"));
//                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, null, jwt);
//
                // 通过 jwt 获取认证信息
                Authentication authentication = JWTProvider.getAuthentication(jwt);

                // 将认证信息存入 Security 上下文中，可以取出来使用，也代表着已认证
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                response.setContentType("application/json;charset=utf-8");
                Result result = Result.create(403, "凭证已失效，请重新登录！");
                PrintWriter out = response.getWriter();
                out.write(objectMapper.writeValueAsString(e.getMessage()));
                out.flush();
                out.close();
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}