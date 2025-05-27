package com.example.ayan.Chat.Application.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexOptions;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.*;

@Document(collection = "user")
@Data
@NoArgsConstructor
public class User {

    @Id
    private ObjectId id;

    @NonNull
    @Indexed(unique = true)
    private String userName;

    @NonNull
    private String password;

    @NonNull
    private String email;


    //private boolean isOnline; //after implementing webSocket
    //private String statusMessage; //through new class

    //private LocalDateTime lastSeen; //after implementing webSocket
    //private LocalDateTime createdAt; //after implementing webSocket


    private List<String> roles;
}
