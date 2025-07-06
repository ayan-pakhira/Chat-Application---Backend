package com.example.ayan.Chat.Application.Service;

import com.example.ayan.Chat.Application.Entity.Group;
import com.example.ayan.Chat.Application.Repository.GroupRepository;
import com.example.ayan.Chat.Application.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Component
@RequiredArgsConstructor
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;


    //creating the group
    public Group createGroup(String name, ObjectId creatorId, List<ObjectId> userIds){

        Group group = new Group();

        group.setName(name);
        group.setCreatedBy(creatorId);

        Set<ObjectId> members = new HashSet<>(userIds);
        members.add(creatorId); //as the creator will also be a member of that group
        group.setMembers(members);

        Set<ObjectId> admins = new HashSet<>();
        admins.add(creatorId);
        group.setAdmins(admins);

        return groupRepository.save(group);
    }


    //adding the group members
    public Group addMembersToGroup(ObjectId groupId, ObjectId requesterId, List<ObjectId> newMembers) {

        Optional<Group> optionalGroup = Optional.of(new Group());
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("group not found"));

        if (!group.getAdmins().contains(requesterId)) {
            throw new RuntimeException("only admin can add members");
        }

        group.getMembers().addAll(newMembers);
        return groupRepository.save(group);

    }


    //assigning normal users as admin of the group
    public Group assigningAdmin(ObjectId groupId, ObjectId requesterId, ObjectId promoteUserId){

        Optional<Group> optionalGroup = Optional.of(new Group());
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("group not found"));

        if(!group.getAdmins().contains(requesterId)){
            throw new RuntimeException("only admins can assign admins");
        }

        if(!group.getMembers().contains(promoteUserId)){
            throw new RuntimeException("to become admin users has to part of the group");
        }

        group.getAdmins().add(promoteUserId);
        return groupRepository.save(group);
    }

    //getting group id to check further if the group exists
    public Group getGroupById(ObjectId groupId){
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("group not found"));
    }


    //get the groups where a particular user is the member
    public List<Group> getUsersGroup(ObjectId userId){
        return groupRepository.findMembersContainById(userId);
    }

}
