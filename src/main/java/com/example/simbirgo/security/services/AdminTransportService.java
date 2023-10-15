package com.example.simbirgo.security.services;

import com.example.simbirgo.entity.Transport;
import com.example.simbirgo.payload.request.TransportDto;
import com.example.simbirgo.repository.TransportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminTransportService {
    @Autowired
    TransportRepository transportRepository;

    public List<Transport> getAllTransports(int start, int count, String transportType) {
        return transportRepository.findTransports(transportType,start,count);
    }

    public ResponseEntity<?> getTransportById(long id) {
        if(transportRepository.existsById(id)){
            return ResponseEntity.ok(transportRepository.findById(id).get());
        }
        return ResponseEntity.badRequest().build();
    }

    public Transport createTransport(TransportDto transportDto) {
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
        return transportRepository.save(transport);
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
