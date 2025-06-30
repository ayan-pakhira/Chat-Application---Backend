package com.example.ayan.Chat.Application.Controller;

import com.example.ayan.Chat.Application.Entity.User;
import com.example.ayan.Chat.Application.Model.UserDTO;
import com.example.ayan.Chat.Application.Repository.UserRepository;
import com.example.ayan.Chat.Application.Service.CustomUserDetailsService;
import com.example.ayan.Chat.Application.Service.UserService;
import com.example.ayan.Chat.Application.Utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/auth")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
   private UserRepository userRepository;

    @Autowired
    private AuthenticationManager auth;


    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;


    //search api for searching the user.
    @GetMapping("/id/{userName}")
    public ResponseEntity<?> searchUser(@PathVariable String userName){
        List<User> users = userService.getUserByUserName(userName);
        return ResponseEntity.ok(users);
    }



}
