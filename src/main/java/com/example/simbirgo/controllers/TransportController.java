package com.example.simbirgo.controllers;


import com.example.simbirgo.entity.Transport;
import com.example.simbirgo.entity.User;
import com.example.simbirgo.payload.request.TransportDto;
import com.example.simbirgo.repository.TransportRepository;
import com.example.simbirgo.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.beans.Beans;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/Transport")
public class TransportController {

    @Autowired
    TransportRepository transportRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/{id}")
    public ResponseEntity<?> addTransport(@PathVariable Long id){
        if(!transportRepository.existsById(id)){
            return ResponseEntity.badRequest().body("Error: Transportation does not exist");
        }
        return ResponseEntity.ok(transportRepository.findById(id));
    }

    public User getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        User user=userRepository.findByUsername(currentUserName).get();
        return user;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> addTransport(@RequestBody TransportDto transportDto){
        User user=getUser();
        Transport transport=new Transport();
        BeanUtils.copyProperties(transport,transportDto);
        try{
            transportRepository.save(transport);
            List<Transport> transportList=user.getTransport();
            transportList.add(transport);
           user.setTransport(transportList);
           userRepository.save(user);
            return ResponseEntity.ok().body("Transport save");
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putTransport(@PathVariable Long id,@RequestBody TransportDto transportDto){
        User user=getUser();
        List<Transport> transportList=user.getTransport();
        if(transportList.stream().filter(transport -> transport.getId()==id).findFirst().orElse(null)!=null){
            Transport transport=transportRepository.findById(id).get();
            BeanUtils.copyProperties(transport,transportDto);
            try{
                transportRepository.save(transport);
                return ResponseEntity.ok().body("Transport save");
            }catch (Exception e){
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.badRequest().body("Error: You are not the owner of the vehicle");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTransport(@PathVariable Long id){
        User user=getUser();
        List<Transport> transportList=user.getTransport();
        if(transportList.stream().filter(transport -> transport.getId()==id).findFirst().orElse(null)!=null){
            Transport transport=transportRepository.findById(id).get();
            transportList.remove(transport);
            user.setTransport(transportList);
            userRepository.save(user);
            transportRepository.delete(transport);
        }
        return ResponseEntity.badRequest().body("Error: You are not the owner of the vehicle");
    }


}
