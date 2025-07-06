package com.example.ayan.Chat.Application.Controller;

import com.example.ayan.Chat.Application.Entity.Group;
import com.example.ayan.Chat.Application.Repository.GroupRepository;
import com.example.ayan.Chat.Application.Repository.UserRepository;
import com.example.ayan.Chat.Application.Service.GroupService;
import com.example.ayan.Chat.Application.Service.JwtService;
import com.example.ayan.Chat.Application.Service.UserService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/group")
@Builder
public class GroupController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupService groupService;

    @Autowired
    private JwtService jwtService;


    //endpoints for creating the group
    @PostMapping("/create")
    public ResponseEntity<?> createGroupOfUsers(@RequestHeader("Authorization") String authHeader,
                                                @RequestBody Map<String, Object> body){
        ObjectId creatorId = jwtService.extractUserId(authHeader);
        String name = (String) body.get("name");

        @SuppressWarnings("unchecked")
        List<String> userIds = (List<String>) body.get("userIds");

        List<ObjectId> memberIds = userIds.stream()
                .map(ObjectId::new)
                .toList();

        Group group = groupService.createGroup(name, creatorId, memberIds);
        return ResponseEntity.ok(group);
    }


    //endpoints for adding members to the group
    @PutMapping("/add-member/{groupId}")
    public ResponseEntity<?> addMembers(@PathVariable ObjectId groupId,
                                        @RequestHeader("Authorization") String authHeader,
                                        @RequestBody List<ObjectId> userIds){
        ObjectId requesterId = jwtService.extractUserId(authHeader);

        List<ObjectId> memberIds = userIds.stream()
                .toList();

        groupService.addMembersToGroup(groupId, requesterId, userIds);
        return ResponseEntity.ok("members added");
    }


    //assigning normal members as admin
    @PutMapping("/assign-admin/{groupId}/{userIds}")
    public ResponseEntity<?> assignadmin(@PathVariable ObjectId groupId,
                                         @PathVariable ObjectId userIds,
                                         @RequestHeader("Authorization") String authHeader){
        ObjectId requesterId = jwtService.extractUserId(authHeader);
        groupService.assigningAdmin(groupId, requesterId, userIds);
        return ResponseEntity.ok("assigned admin");
    }


    //get group id
    @GetMapping("/get-group/{groupId}")
    public ResponseEntity<?> getGroup(@PathVariable ObjectId groupId){
        Group group = groupService.getGroupById(groupId);

        return ResponseEntity.ok("group found");

    }



    //find a user attached to numbers of group
    @GetMapping("/my-groups")
    public ResponseEntity<?> getMyGroups(@RequestHeader("Authorization") String authHeader){
        ObjectId userIds = jwtService.extractUserId(authHeader);
        return ResponseEntity.ok(groupService.getUsersGroup(userIds));
    }
}
