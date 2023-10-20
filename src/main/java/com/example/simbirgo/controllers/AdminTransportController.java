package com.example.simbirgo.controllers;

import com.example.simbirgo.entity.Transport;
import com.example.simbirgo.payload.request.TransportDto;
import com.example.simbirgo.security.services.AdminTransportService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
    private AdminTransportService adminTransportService;


    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "JWT")
    public List<Transport> getAllTransports(@RequestParam int start, @RequestParam int count, @RequestParam(required = false) String transportType) {
        List<Transport> transports = adminTransportService.getAllTransports(start, count, transportType);
        return transports;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<?> getTransportById(@PathVariable long id) {
        return adminTransportService.getTransportById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<?> createTransport(@RequestBody TransportDto transportDto) {
        return adminTransportService.createTransport(transportDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<?> updateTransport(@PathVariable long id, @RequestBody TransportDto transportDto) {
        return adminTransportService.updateTransport(id, transportDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "JWT")
    private ResponseEntity<?> deleteTransport(@PathVariable long id){
        return adminTransportService.deleteTransport(id);
    }
}
