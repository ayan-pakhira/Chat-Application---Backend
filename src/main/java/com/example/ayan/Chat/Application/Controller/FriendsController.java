package com.example.ayan.Chat.Application.Controller;

import com.example.ayan.Chat.Application.Entity.User;
import com.example.ayan.Chat.Application.Repository.UserRepository;
import com.example.ayan.Chat.Application.Service.FriendService;
import com.example.ayan.Chat.Application.Service.JwtService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/friends")
public class FriendsController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FriendService friendService;

    @Autowired
    private JwtService jwtService;


    @PostMapping("/send-request/{receiverId}")
    public ResponseEntity<?> sentFriendRequest(@PathVariable ObjectId receiverId,
                                               @RequestHeader("Authorization") String authHeader){
        ObjectId senderId = jwtService.extractUserId(authHeader);
        friendService.sendFriendRequest(senderId, receiverId);
        return ResponseEntity.ok("friend request sent");
    }


    @PostMapping("/accept-request/{senderId}")
    public ResponseEntity<?> acceptFriendRequest(@PathVariable ObjectId senderId,
                                                 @RequestHeader("Authorization") String authHeader){
        ObjectId receiverId = jwtService.extractUserId(authHeader);
        friendService.acceptFriendRequest(senderId, receiverId);
        return ResponseEntity.ok("friend request accepted");
    }


    @PostMapping("/reject-request/{senderId}")
    public ResponseEntity<?> rejectFriendRequest(@PathVariable ObjectId senderId,
                                                 @RequestHeader("Authorization") String authHeader){
        ObjectId receiverId = jwtService.extractUserId(authHeader);
        friendService.rejectFriendRequest(senderId, receiverId);
        return ResponseEntity.ok("friend request rejected");
    }


    //fetching the friend list, friend request received, friend request sent of a user
    @GetMapping("/friend-list/{userName}")
    public ResponseEntity<?> getFriendList(@PathVariable String userName,
                                           @RequestHeader("Authorization") String authHeader){
        ObjectId userId = jwtService.extractUserId(authHeader);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found"));

        if(!user.getUserName().equals(userName)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("user is invalid");
        }

        List<ObjectId> friendList = user.getFriends();
        List<User> users = userRepository.findAllById(friendList);

        List<ObjectId> friendRequestSent = user.getFriendRequestSent();
        List<User> userSent = userRepository.findAllById(friendRequestSent);

        List<ObjectId> friendRequestReceived = user.getFriendRequestReceived();
        List<User> userReceived = userRepository.findAllById(friendRequestReceived);

        return ResponseEntity.ok(userReceived);

    }
}
