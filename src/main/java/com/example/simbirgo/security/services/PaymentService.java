package com.example.simbirgo.security.services;

import com.example.simbirgo.entity.ERole;
import com.example.simbirgo.entity.User;
import com.example.simbirgo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    UserRepository userRepository;
    public User getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        User user=userRepository.findByUsername(currentUserName).get();
        return user;
    }

    public ResponseEntity<?> getMoney(Long accountId){
        User user=getUser();
        if(user.getRoles().stream().filter(role -> role.getName()== ERole.ROLE_ADMIN).findFirst().orElse(null)!=null){
            User user2=userRepository.findById(accountId).orElse(null);
            if(user2!=null){
                Double balance=user2.getBalance()+250_000;
                user2.setBalance(balance);
                return ResponseEntity.ok().body(userRepository.save(user2));
            }
            return ResponseEntity.badRequest().body("User not found!");
        }
        Double balance=user.getBalance()+250_000;
        user.setBalance(balance);
        return ResponseEntity.ok().body(userRepository.save(user));
    }

}
