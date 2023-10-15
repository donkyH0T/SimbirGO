package com.example.simbirgo.controllers;


import com.example.simbirgo.entity.User;
import com.example.simbirgo.payload.request.UserDto;
import com.example.simbirgo.security.services.AdminAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/Admin/Account")
public class AdminAccountController {

    @Autowired
    private AdminAccountService adminAccountService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDto> getAllAccounts(@RequestParam int start, @RequestParam int count) {
        return adminAccountService.getAllAccounts(start, count);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public User getAccountById(@PathVariable Long id) {
        return adminAccountService.getAccountById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createAccount(@RequestBody UserDto accountDto) {
        return adminAccountService.createAccount(accountDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateAccount(@PathVariable Long id, @RequestBody UserDto accountDto) {
       return adminAccountService.updateAccount(id, accountDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteAccount(@PathVariable Long id) {
       return adminAccountService.deleteAccount(id);
    }
}
