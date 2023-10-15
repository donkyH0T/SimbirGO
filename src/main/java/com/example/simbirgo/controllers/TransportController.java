package com.example.simbirgo.controllers;


import com.example.simbirgo.entity.Transport;
import com.example.simbirgo.entity.User;
import com.example.simbirgo.payload.request.TransportDto;
import com.example.simbirgo.repository.TransportRepository;
import com.example.simbirgo.repository.UserRepository;
import com.example.simbirgo.security.services.TransportService;
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
    TransportService transportService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getTransportById(@PathVariable Long id){
        return transportService.getTransportById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> addTransport(@RequestBody TransportDto transportDto){
        return transportService.addTransport(transportDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> putTransport(@PathVariable Long id,@RequestBody TransportDto transportDto){
        return transportService.putTransport(id, transportDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTransport(@PathVariable Long id){
       return transportService.deleteTransport(id);
    }
}
