package com.example.ayan.Chat.Application.Config;

import com.example.ayan.Chat.Application.Filter.JwtFilter;
import com.example.ayan.Chat.Application.Service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Autowired
        private CustomUserDetailsService customService;

       @Autowired
        private JwtFilter jwtFilter;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

            return http.csrf(customizer -> customizer.disable())

                    .authorizeHttpRequests(requests -> requests
                            .requestMatchers("/public/**").permitAll()
                            .requestMatchers("/user/auth/**").hasAnyRole("USER", "ADMIN")
                            .requestMatchers("/admin/auth/**").hasRole("ADMIN")
                            .requestMatchers("/friends/**").hasAnyRole("ADMIN", "USER")
                            .requestMatchers("/group/**").hasAnyRole("ADMIN", "USER")







                            .anyRequest().authenticated())

                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))

                    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)

                    .build();
        }



        @Bean
        public AuthenticationProvider authenticationProvider(){

            DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

            provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
            provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
            provider.setUserDetailsService(customService);

            return provider;
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(customService).passwordEncoder(passwordEncoder());
//    }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
            return config.getAuthenticationManager();
        }



    }



