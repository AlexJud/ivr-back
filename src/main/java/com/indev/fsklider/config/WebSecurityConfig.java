package com.indev.fsklider.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.header.writers.frameoptions.StaticAllowFromStrategy;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

import java.net.URI;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .authorizeRequests()
////                .antMatchers("/api/json").permitAll()
//                .antMatchers("/api/json").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .httpBasic();
        .csrf()
                .disable()
//                .ignoringAntMatchers("")
                .exceptionHandling()
                .and()
                .authorizeRequests()
                .antMatchers("/**", "/wss/**", "/hello/**").permitAll()
                .antMatchers("/api/json").authenticated()
                .and()
                .cors()
        .and()
                .headers()
                    .contentSecurityPolicy("frame-src https://192.168.1.41:4200 https://192.168.1.41:8080/*; frame-ancestors https://192.168.1.41:4200*/")
                .and()
//                .frameOptions()
//                .sameOrigin();
                .addHeaderWriter(new XFrameOptionsHeaderWriter(new StaticAllowFromStrategy(new URI("https://192.168.1.41:4200"))));
//                .addHeaderWriter(new XFrameOptionsHeaderWriter(new StaticAllowFromStrategy(new URI("https://localhost:4200"))));
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("user").password(passwordEncoder().encode("password"))
                .authorities("ADMIN");
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
