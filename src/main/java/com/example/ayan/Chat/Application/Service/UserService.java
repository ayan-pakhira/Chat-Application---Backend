package com.example.ayan.Chat.Application.Service;

import com.example.ayan.Chat.Application.Entity.User;
import com.example.ayan.Chat.Application.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean saveEntry(User user){
        userRepository.save(user);
        return true;
    }

    public List<User> getAll(){
        List<User> allUsers = userRepository.findAll();
        return allUsers;

    }
}
