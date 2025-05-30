package com.example.ayan.Chat.Application.Controller;

import com.example.ayan.Chat.Application.Entity.User;
import com.example.ayan.Chat.Application.Repository.UserRepository;
import com.example.ayan.Chat.Application.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
   private UserRepository userRepository;


    @PostMapping("/register-user")
    public ResponseEntity<?> registerUser(@RequestBody User user){
        User savedUser = userService.saveUserEntry(user);

        return ResponseEntity.ok("User has created");
    }


    @DeleteMapping("/delete-users")
    public ResponseEntity<?> deleteAllUsers(){
        userService.deleteAll();
        return ResponseEntity.ok("Deleted all users");
    }

}
