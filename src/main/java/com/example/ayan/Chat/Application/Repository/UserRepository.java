package com.example.ayan.Chat.Application.Repository;

import java.util.*;
import com.example.ayan.Chat.Application.Entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {

    User findByUserName(String userName);
    List<User> findByUserNameContainingIgnoreCase(String userName);
    User findByEmail(String email);
}
