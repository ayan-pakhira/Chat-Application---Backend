package com.example.ayan.Chat.Application.Controller;

import com.example.ayan.Chat.Application.Entity.User;
import com.example.ayan.Chat.Application.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public boolean registerUser(@RequestBody User user){
        userService.saveEntry(user);
        return true;
    }

    @GetMapping("/data")
    public List<User> getUsersDetails(){
        List<User> allUsers = userService.getAll();
        return allUsers;
    }
}
