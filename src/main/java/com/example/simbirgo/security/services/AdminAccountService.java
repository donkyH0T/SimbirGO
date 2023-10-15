package com.example.simbirgo.security.services;

import com.example.simbirgo.entity.ERole;
import com.example.simbirgo.entity.Role;
import com.example.simbirgo.entity.User;
import com.example.simbirgo.payload.request.UserDto;
import com.example.simbirgo.repository.RoleRepository;
import com.example.simbirgo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdminAccountService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;
    public List<UserDto> getAllAccounts(int start, int count) {
        List<User> userList=userRepository.findAllUsers(start,count);
       return userList.stream().map(UserDto::toDto).collect(Collectors.toList());
    }

    public User getAccountById(Long id) {
        if(userRepository.existsById(id)){
            return userRepository.findById(id).get();
        }
            return null;
    }

    public ResponseEntity<?> createAccount(UserDto accountDto) {
        if(userRepository.existsByUsername(accountDto.getUsername())){
            return ResponseEntity.badRequest().body("Account exists");
        }
        User user=new User();
        user.setUsername(accountDto.getUsername());
        Set<Role> roleList=new HashSet<>();
        roleList.add(roleRepository.findByName(ERole.ROLE_USER).get());
        if(accountDto.getIsAdmin()){
            roleList.add(roleRepository.findByName(ERole.ROLE_ADMIN).get());
        }
        user.setPassword(encoder.encode(accountDto.getPassword()));
        user.setRoles(roleList);
        user.setBalance(accountDto.getBalance());
       return ResponseEntity.ok(userRepository.save(user));
    }

    public ResponseEntity<?> updateAccount(Long id, UserDto accountDto) {
        if(userRepository.existsByUsername(accountDto.getUsername())){
            return ResponseEntity.badRequest().body("Account exists");
        }
        if(userRepository.existsById(id)){
           User user=userRepository.findById(id).get();
            user.setUsername(accountDto.getUsername());
            user.setPassword(encoder.encode(accountDto.getPassword()));
            Set<Role> roleList=user.getRoles();
            if(accountDto.getIsAdmin()){
               if(roleList.stream().filter(role -> role.getName()==ERole.ROLE_ADMIN).findFirst().orElse(null)==null){
                   roleList.add(roleRepository.findByName(ERole.ROLE_ADMIN).get());
               }
            }else{
                if(roleList.stream().filter(role -> role.getName()==ERole.ROLE_ADMIN).findFirst().orElse(null)!=null){
                    roleList.remove(roleRepository.findByName(ERole.ROLE_ADMIN).get());
                }
            }
            user.setRoles(roleList);
            user.setBalance(accountDto.getBalance());
            return ResponseEntity.ok(userRepository.save(user));
        }
        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<?> deleteAccount(Long id) {
        if(userRepository.existsById(id)){
            User user=userRepository.findById(id).get();
            user.setRoles(null);
            userRepository.saveAndFlush(user);
            userRepository.delete(user);
            return ResponseEntity.ok("User deleted");
        }
        return ResponseEntity.badRequest().build();
    }
}
