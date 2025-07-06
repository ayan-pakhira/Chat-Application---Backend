package com.example.ayan.Chat.Application.Model;

import com.example.ayan.Chat.Application.Entity.GroupMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupMessageDTO {

    private String id;
    private String groupId;
    private String senderId;
    private String content;
    private Date timeStamp;

    //constructor of GroupMessageDTO to map these data with the GroupMessage data
    public GroupMessageDTO (GroupMessage message){
        this.id = message.getId().toHexString();
        this.groupId = message.getGroupId().toHexString();
        this.senderId = message.getSenderId().toHexString();
        this.content = message.getContent();
        this.timeStamp = message.getTimeStamp();
    }
}
