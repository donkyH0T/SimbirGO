package com.example.simbirgo.payload.request;

import com.example.simbirgo.entity.ERole;
import com.example.simbirgo.entity.Role;
import com.example.simbirgo.entity.User;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private String username;
    private String password;
    private Boolean isAdmin;
    private Double balance;


    public static UserDto toDto(User user){
        UserDto userDto=new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setPassword(user.getPassword());
        userDto.setBalance(user.getBalance());
       if(user.getRoles().stream().filter(role -> role.getName()==ERole.ROLE_ADMIN).findFirst().orElse(null)!=null){
           userDto.setIsAdmin(true);
       }else{
         userDto.setIsAdmin(false);
       }
       return userDto;
    }
}
