package com.mblog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class BasicConfiguration {

    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user = User.withUsername("user")
            .password(passwordEncoder.encode("password"))
            .roles("USER")
            .build();

        UserDetails admin = User.withUsername("admin")
            .password(passwordEncoder.encode("admin"))
            .roles("USER", "ADMIN")
            .build();

        return new InMemoryUserDetailsManager(user, admin);
    }
// 基础过滤器
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
         http
                 .csrf(csrf-> csrf.disable())
                 .cors(Customizer.withDefaults()) // 使用默认的CORS配置
                 .authorizeHttpRequests(request -> request
                        //不需要通过登录验证就可以被访问的资源路径
                        //前面是资源的访问路径、后面是资源的名称或者叫资源ID
                 .requestMatchers("/login").permitAll()
                 .requestMatchers("/blogs").permitAll()
                 .anyRequest().authenticated())
//
//                .formLogin((form) -> form
//                        .loginPage("/login")
//                        .permitAll()
//                )
                 .logout((logout) -> logout.permitAll());
         ;
//            .httpBasic(Customizer.withDefaults())
        return  http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return encoder;
    }
}
