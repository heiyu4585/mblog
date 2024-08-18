package com.mblog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import java.util.Map;

@Component
public class JWTProvider {

    private static final String AUTHORITIES_KEY = "admin,user,visitor";

    private static SecretKey secretKey;

    @Value("${jwt.secret}")
    public void setJwtSecret(String secret) {
        secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    private static int jwtExpirationInMs;

    @Value("${jwt.expire}")
    public void setJwtExpirationInMs(int expire) {
        jwtExpirationInMs = expire;
    }

    // generate JWT token
    public static String generateToken(String subject) {
        long currentTimeMillis = System.currentTimeMillis();
        Date expirationDate = new Date(currentTimeMillis + jwtExpirationInMs * 1000);
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .setSubject(subject)
                .setExpiration(expirationDate)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public static Authentication getAuthentication(String token) {
        Claims claims =
                Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
        // 从jwt获取用户权限列
        String permissionString = (String) claims.get(AUTHORITIES_KEY);

//        List<SimpleGrantedAuthority> authorities = Arrays.stream(AUTHORITIES_KEY.split(",")).map(SimpleGrantedAuthority::new)
//                        .collect(Collectors.toList());
        List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("AUTHORITIES_KEY"));

        // 获取 username
        String username = claims.getSubject();

        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }

    // validate Jwt token
    public static boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parse(token);
            Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
//            String tokenUsername = claims.getSubject();
            Date expiration = claims.getExpiration();
//            return expiration.before(new Date());
            return true;
        } catch (MalformedJwtException e) {
//            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
//            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
//            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
//            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
    /**
     * 获取tokenBody同时校验token是否有效（无效则会抛出异常）
     *
     * @param token
     * @return
     */
    public static Claims getTokenBody(String token) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token.replace("Bearer", "")).getBody();
        return claims;
    }

    public static boolean judgeTokenIsExist(String token) {
        return token != null && !"".equals(token) && !"null".equals(token);
    }
}