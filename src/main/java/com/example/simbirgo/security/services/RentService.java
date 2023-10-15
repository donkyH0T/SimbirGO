package com.example.simbirgo.security.services;

import com.example.simbirgo.entity.Rent;
import com.example.simbirgo.entity.Transport;
import com.example.simbirgo.entity.User;
import com.example.simbirgo.repository.RentRepository;
import com.example.simbirgo.repository.TransportRepository;
import com.example.simbirgo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class RentService {
    @Autowired
    TransportRepository transportRepository;

    @Autowired
    RentRepository rentRepository;

    @Autowired
    UserRepository userRepository;


    public ResponseEntity<?> getAvailableTransport(double latitude,double longitude,double radius,String type) {
        List<Transport> transportList=transportRepository.findTransportByTypeAndWithinRadius(type,latitude,longitude,radius);
        if(transportList.isEmpty()){
            return ResponseEntity.ok("Транспорта нет");
        }
        return ResponseEntity.ok(transportList);
    }
    public User getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        User user=userRepository.findByUsername(currentUserName).get();
        return user;
    }

    public ResponseEntity<?> getRentInfoById(Long rentId) {
        User user=getUser();
        Rent rent= rentRepository.findById(rentId).orElse(null);
        if(rent==null){
            return ResponseEntity.badRequest().body("Error: Rent not found");
        }
        if(rent.getUserId().equals(user.getId())){
            return ResponseEntity.ok(rent);
        }
        if(user.getTransport().contains(transportRepository.findById(rent.getTransportId()).get())){
            return ResponseEntity.ok(rent);
        }

        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<?> getRentHistoryForCurrentUser() {
        User user=getUser();
        return ResponseEntity.ok(rentRepository.findRentsByUserId(user.getId()));
    }

    public ResponseEntity<?> getRentHistoryForTransport(Long transportId) {
        User user=getUser();
        Transport transport=transportRepository.findById(transportId).orElse(null);
        if(transport!=null){
            if(user.getTransport().contains(transport)){
                return ResponseEntity.ok(rentRepository.findRentsByTransportId(transportId));
            }
            return ResponseEntity.badRequest().body("Error: You are not the owner of the vehicle");
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<?> rentTransport(Long transportId,String rentType) {
        User user=getUser();
        Transport transport=transportRepository.findById(transportId).orElse(null);
        if(transport!=null){
            if(!user.getTransport().contains(transport)){
                Rent rent=new Rent();
                rent.setTransportId(transportId);
                rent.setUserId(user.getId());
                LocalDateTime currentDateTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
                String formattedDateTime = currentDateTime.format(formatter);
                rent.setTimeStart(formattedDateTime);
                rent.setPriceType(rentType);
                rentRepository.save(rent);
                return ResponseEntity.ok(rentRepository.save(rent));
            }
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<?> endRent(Long rentId,double lat,double lon) {
        User user=getUser();
        Rent rent=rentRepository.findById(rentId).orElse(null);
        if(rent!=null || rent.getUserId().equals(user.getId())){
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
            String formattedDateTime = currentDateTime.format(formatter);
            rent.setTimeEnd(formattedDateTime);
            rentRepository.save(rent);
            Transport transport=transportRepository.findById(rent.getTransportId()).get();
            transport.setLatitude(lat);
            transport.setLongitude(lon);
            transportRepository.saveAndFlush(transport);
            return ResponseEntity.ok("Rent Completed!");
        }
        return ResponseEntity.badRequest().build();
    }
}
