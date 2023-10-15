package com.example.simbirgo.controllers;

import com.example.simbirgo.entity.Rent;
import com.example.simbirgo.payload.request.RentDto;
import com.example.simbirgo.security.services.AdminRentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/Admin")
public class AdminRentController {

    @Autowired
    private AdminRentService rentService;

    @GetMapping("/Rent/{rentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getRentById(@PathVariable long rentId) {
        return rentService.getRentById(rentId);
    }

    @GetMapping("/UserHistory/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Rent> getUserRentHistory(@PathVariable long userId) {
        return rentService.getUserRentHistory(userId);
    }

    @GetMapping("/TransportHistory/{transportId}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Rent> getTransportRentHistory(@PathVariable long transportId) {
        return rentService.getTransportRentHistory(transportId);
    }

    @PostMapping("/Rent")
    @PreAuthorize("hasRole('ADMIN')")
    public Rent createRent(@RequestBody RentDto rentDto) {
        return rentService.createRent(rentDto);
    }

    @PostMapping("Rent/End/{rentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> endRent(@PathVariable long rentId, @RequestParam double lat, @RequestParam double lon) {
        return rentService.endRent(rentId, lat, lon);
    }
}
