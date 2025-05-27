package com.example.ayan.Chat.Application.Service;

import com.example.ayan.Chat.Application.Entity.User;
import com.example.ayan.Chat.Application.Model.UserPrincipal;
import com.example.ayan.Chat.Application.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      User user = userRepository.findByUserName(username);

      if(user == null){
          throw new UsernameNotFoundException("user not found by this username " + username);
      }

      return new UserPrincipal(user);
    }
}
