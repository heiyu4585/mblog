package com.mblog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;


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
//                 .authorizeHttpRequests(request -> request
//                        //不需要通过登录验证就可以被访问的资源路径
//                        //前面是资源的访问路径、后面是资源的名称或者叫资源ID
//                         .requestMatchers("/login").permitAll() // /public 接口可以公开访问
//                         .requestMatchers("/admin/**").hasRole("admin")
//                         .requestMatchers(HttpMethod.GET).permitAll()
//                         .requestMatchers(HttpMethod.POST).hasAuthority("admin")
//                 .anyRequest().authenticated())
//
//                .formLogin((form) -> form
//                        .loginPage("/login")
//                        .permitAll()
//                )
        ;

        http.securityMatcher("/admin/**").authorizeHttpRequests(
                authorize -> authorize
                .requestMatchers("/admin/login").permitAll()
//                .requestMatchers("/admin/addBlog").permitAll()
                        // /public 接口可以公开访问
//                .requestMatchers("/admin/addBlog").hasAuthority("ADMIN") // /admin 接口需要 ADMIN 权限
                .anyRequest().authenticated()
        ); // 其他的所以接口都需要认证才可以访问

                http.logout((logout) -> logout.permitAll());

//            .httpBasic(Customizer.withDefaults())


        // 设置异常的EntryPoint的处理
        http.exceptionHandling(exceptions -> exceptions
//                 未登录
                .authenticationEntryPoint(new MyAuthenticationEntryPoint())
//                 权限不足
                .accessDeniedHandler(new MyAccessDeniedHandler()));

        http
//                .addFilter(new JwtAuthenticationFilter(authenticationManager()));
                .addFilterBefore(new JWTFilter(), LogoutFilter.class);
        return  http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return encoder;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
