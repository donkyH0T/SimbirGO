package com.example.simbirgo.security.services;

import com.example.simbirgo.entity.Transport;
import com.example.simbirgo.entity.User;
import com.example.simbirgo.payload.request.TransportDto;
import com.example.simbirgo.repository.TransportRepository;
import com.example.simbirgo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminTransportService {
    @Autowired
    TransportRepository transportRepository;
    @Autowired
    UserRepository userRepository;

    public List<Transport> getAllTransports(int start, int count, String transportType) {
        return transportRepository.findTransports(transportType,start,count);
    }

    public ResponseEntity<?> getTransportById(long id) {
        if(transportRepository.existsById(id)){
            return ResponseEntity.ok(transportRepository.findById(id).get());
        }
        return ResponseEntity.badRequest().build();
    }
    public User getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        User user=userRepository.findByUsername(currentUserName).get();
        return user;
    }

    public ResponseEntity<?> createTransport(TransportDto transportDto) {
        Transport transport=new Transport();
        transport.setCanBeRented(transportDto.getCanBeRented());
        transport.setTransportType(transportDto.getTransportType());
        transport.setModel(transportDto.getModel());
        transport.setColor(transportDto.getColor());
        transport.setIdentifier(transportDto.getIdentifier());
        transport.setDescription(transportDto.getDescription());
        transport.setLatitude(transportDto.getLatitude());
        transport.setLongitude(transportDto.getLongitude());
        transport.setMinutePrice(transportDto.getMinutePrice());
        transport.setDayPrice(transportDto.getDayPrice());
        transportRepository.save(transport);
        User user=getUser();
        List<Transport> transportList=user.getTransport();
        transportList.add(transport);
        user.setTransport(transportList);
        userRepository.save(user);
        return ResponseEntity.ok("Transport save");
    }

    public ResponseEntity<?> updateTransport(long id, TransportDto transportDto) {
        if(!transportRepository.existsById(id)){
            return ResponseEntity.badRequest().build();
        }
        Transport transport=transportRepository.findById(id).get();
        transport.setCanBeRented(transportDto.getCanBeRented());
        transport.setTransportType(transportDto.getTransportType());
        transport.setModel(transportDto.getModel());
        transport.setColor(transportDto.getColor());
        transport.setIdentifier(transportDto.getIdentifier());
        transport.setDescription(transportDto.getDescription());
        transport.setLatitude(transportDto.getLatitude());
        transport.setLongitude(transportDto.getLongitude());
        transport.setMinutePrice(transportDto.getMinutePrice());
        transport.setDayPrice(transportDto.getDayPrice());
        return ResponseEntity.ok(transportRepository.save(transport));
    }

    public ResponseEntity<?> deleteTransport(Long id) {
        if(!transportRepository.existsById(id)){
            return ResponseEntity.badRequest().build();
        }
        transportRepository.deleteById(id);
        return ResponseEntity.ok("Transport deleted!");
    }
}
