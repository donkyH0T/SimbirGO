package com.example.simbirgo.security.services;

import com.example.simbirgo.entity.Rent;
import com.example.simbirgo.entity.Transport;
import com.example.simbirgo.payload.request.RentDto;
import com.example.simbirgo.repository.RentRepository;
import com.example.simbirgo.repository.TransportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AdminRentService {
    @Autowired
    RentRepository rentRepository;

    @Autowired
    TransportRepository transportRepository;

    public ResponseEntity<?> getRentById(long rentId) {
        if(rentRepository.existsById(rentId)){
            return ResponseEntity.ok(rentRepository.findById(rentId).get());
        }
        return ResponseEntity.badRequest().body("Error: rentId not exists!");
    }

    public List<Rent> getUserRentHistory(long userId) {
        return rentRepository.findRentsByUserId(userId);
    }

    public List<Rent> getTransportRentHistory(long transportId) {
        return rentRepository.findRentsByTransportId(transportId);
    }

    public Rent createRent(RentDto rentDto) {
        Rent rent=new Rent();
        rent.setTransportId(rentDto.getTransportId());
        rent.setUserId(rentDto.getUserId());
        rent.setTimeStart(rentDto.getTimeStart());
        rent.setTimeEnd(rentDto.getTimeEnd());
        rent.setPriceOfUnit(rentDto.getPriceOfUnit());
        rent.setPriceType(rentDto.getPriceType());
        rent.setFinalPrice(rentDto.getFinalPrice());
        return rentRepository.save(rent);
    }

    public ResponseEntity<?> endRent(long rentId, double lat, double lon) {
        if(!rentRepository.existsById(rentId)){
            return ResponseEntity.badRequest().body("Error: Rent not exists!");
        }
        Rent rent=rentRepository.findById(rentId).get();
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);
        rent.setTimeEnd(formattedDateTime);
        rentRepository.saveAndFlush(rent);
        Transport transport=transportRepository.findById(rent.getTransportId()).get();
        transport.setLatitude(lat);
        transport.setLongitude(lon);
        transportRepository.saveAndFlush(transport);
        return ResponseEntity.ok("Rent Completed!");
    }
}
