package com.example.simbirgo.controllers;


import com.example.simbirgo.payload.request.LoginRequest;
import com.example.simbirgo.payload.request.SignupRequest;
import com.example.simbirgo.payload.request.UpdateRequest;
import com.example.simbirgo.security.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController

@RequestMapping("/api/Account")
public class AccountController {

   @Autowired
    AccountService accountService;



    @GetMapping("/Me")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")

    public ResponseEntity<?> me(){
        return accountService.me();
    }

    @PostMapping("/SignIn")
    public ResponseEntity<?> signIn(@RequestBody LoginRequest loginRequest){
        return accountService.signIn(loginRequest);
    }

    @PostMapping("/SignUp")
    public ResponseEntity<?> signUp(@RequestBody SignupRequest signUpRequest){
        return accountService.signUp(signUpRequest);
    }

    @PostMapping("/SignOut")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> signOut(){
        return accountService.signOut();
    }

    @PutMapping("/Update")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestBody UpdateRequest updateRequest){
        return accountService.update(updateRequest);
    }
}
