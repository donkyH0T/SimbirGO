package com.example.simbirgo.controllers;

import com.example.simbirgo.entity.Transport;
import com.example.simbirgo.payload.request.TransportDto;
import com.example.simbirgo.security.services.AdminTransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/Admin/Transport")
public class AdminTransportController {

    @Autowired
    private AdminTransportService transportService;


    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Transport> getAllTransports(@RequestParam int start, @RequestParam int count, @RequestParam(required = false) String transportType) {
        List<Transport> transports = transportService.getAllTransports(start, count, transportType);
        return transports;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getTransportById(@PathVariable long id) {
        return transportService.getTransportById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Transport createTransport(@RequestBody TransportDto transportDto) {
        return transportService.createTransport(transportDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateTransport(@PathVariable long id, @RequestBody TransportDto transportDto) {
        return transportService.updateTransport(id, transportDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    private ResponseEntity<?> deleteTransport(@PathVariable Long id){
        return transportService.deleteTransport(id);
    }
}
