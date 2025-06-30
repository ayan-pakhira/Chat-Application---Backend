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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {

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


    @PostMapping("/register-user")
    public ResponseEntity<?> registerUser(@RequestBody User user){
        User savedUser = userService.saveUserEntry(user);

        return ResponseEntity.ok("User has created");
    }

    @PostMapping("/api/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDTO user){

        try{
            auth.authenticate(new UsernamePasswordAuthenticationToken
                    (user.getEmail(), user.getPassword()));

            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);

        } catch (Exception e) {
            log.error("Exception occurred while createAuthenticationToken ", e);
            return new ResponseEntity<>("incorrect username or password", HttpStatus.BAD_REQUEST);
        }
    }


    //this is only for testing purpose to delete all the users one at a time.
    @DeleteMapping("/delete-users")
    public ResponseEntity<?> deleteAllUsers(){
        userService.deleteAll();
        return ResponseEntity.ok("Deleted all users");
    }



}
