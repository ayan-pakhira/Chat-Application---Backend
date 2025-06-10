package com.example.ayan.Chat.Application.Controller;

import com.example.ayan.Chat.Application.Entity.User;
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


    @PostMapping("/register-user")
    public ResponseEntity<?> registerUser(@RequestBody User user){
        User savedUser = userService.saveUserEntry(user);

        return ResponseEntity.ok("User has created");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user){

        try{
            auth.authenticate(new UsernamePasswordAuthenticationToken
                    (user.getUserName(), user.getPassword()));

            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);

        } catch (Exception e) {
            log.error("Exception occurred while createAuthenticationToken ", e);
            return new ResponseEntity<>("incorrect username or password", HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("/delete-users")
    public ResponseEntity<?> deleteAllUsers(){
        userService.deleteAll();
        return ResponseEntity.ok("Deleted all users");
    }

}
