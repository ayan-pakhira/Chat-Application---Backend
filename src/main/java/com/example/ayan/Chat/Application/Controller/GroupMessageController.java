package com.example.ayan.Chat.Application.Controller;

import com.example.ayan.Chat.Application.Entity.GroupMessage;
import com.example.ayan.Chat.Application.Model.GroupMessageDTO;
import com.example.ayan.Chat.Application.Service.GroupMessageService;
import com.example.ayan.Chat.Application.Service.JwtService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/group-message")
public class GroupMessageController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private GroupMessageService groupMessageService;


    //saving the sent messages in the database
    @PostMapping("/send-message")
    public ResponseEntity<?> sendMessages(@RequestHeader("Authorization") String authHeader,
                                          @RequestBody Map<String, String> body){
        ObjectId senderId = jwtService.extractUserId(authHeader);

        ObjectId groupId = new ObjectId( body.get("groupId"));
        String content = body.get("content");

        GroupMessage message =new GroupMessage();
        message.setSenderId(senderId);
        message.setGroupId(groupId);
        message.setContent(content);

        GroupMessage saved = groupMessageService.saveMessage(message);

        return ResponseEntity.ok(saved);


    }


    //fetching the saved messages from the database
    @GetMapping("/get-message/{groupId}")
    public ResponseEntity<?> getGroupMessageById(@PathVariable ObjectId groupId){
        List<GroupMessage> messages = groupMessageService.getGroupMessagesById(groupId);

        List<GroupMessageDTO> structuredMessages = messages.stream()
                .map(GroupMessageDTO::new)
                .toList();

        return ResponseEntity.ok(structuredMessages);
    }
}
