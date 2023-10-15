package com.example.simbirgo.security.services;

import com.example.simbirgo.entity.Transport;
import com.example.simbirgo.entity.User;
import com.example.simbirgo.payload.request.TransportDto;
import com.example.simbirgo.repository.TransportRepository;
import com.example.simbirgo.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransportService {
    @Autowired
    TransportRepository transportRepository;

    @Autowired
    UserRepository userRepository;

    public ResponseEntity<?> addTransport(Long id){
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

    public ResponseEntity<?> addTransport(TransportDto transportDto){
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

    public ResponseEntity<?> putTransport(Long id,TransportDto transportDto){
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

    public ResponseEntity<?> deleteTransport(Long id){
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
