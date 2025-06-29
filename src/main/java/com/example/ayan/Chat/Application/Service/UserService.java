package com.example.ayan.Chat.Application.Service;

import com.example.ayan.Chat.Application.Entity.User;
import com.example.ayan.Chat.Application.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authManager;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    //to save the normal users
    //for user controller
    public User saveUserEntry(User user){

        user.setUserName(user.getUserName());
        user.setPassword(encoder.encode(user.getPassword()));
        user.setEmail(user.getEmail());
        user.setRoles(List.of("USER"));
        return userRepository.save(user);

    }

    //to save the admin entry and admin users
    //for admin controller
    public User saveAdminEntry(String userName, String password, String email){

        User admin = new User();

        admin.setUserName(userName);
        admin.setPassword(encoder.encode(password));
        admin.setEmail(email);
        admin.setRoles(Arrays.asList("ADMIN", "USER"));
        return userRepository.save(admin);
    }

    //to get the all users by admin
    //for admin controller
    public List<User> getAll(){
        return userRepository.findAll();
    }

    //search the users and fetch them if they are registered
//    public List<User> searchUser(String search, String currentUserId){
//        currentUserId = getCurrentUserId();
//        List<User> users = userRepository.findByUsernameContainingIgnoreCaseOrEmailContainingIgnoreCase(search, search);
//        String finalCurrentUserId = currentUserId;
//        return users.stream()
//                .filter(user -> !user.getId().equals(finalCurrentUserId))
//                .collect(Collectors.toList());
//    }



    public boolean deleteAll(){
        userRepository.deleteAll();
        return true;
    }




}
