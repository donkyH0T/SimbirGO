package com.example.simbirgo.controllers;


import com.example.simbirgo.security.services.RentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/Rent")
public class RentController {
    @Autowired
    RentService rentService;

    @GetMapping("/Transport")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<?> getAvailableTransport(@RequestParam double latitude, @RequestParam double longitude, @RequestParam double radius, @RequestParam String type) {
        return rentService.getAvailableTransport(latitude,longitude,radius,type);
    }

    @GetMapping("/{rentId}")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<?> getRentInfoById(@PathVariable Long rentId) {
        return rentService.getRentInfoById(rentId);
    }

    @GetMapping("/MyHistory")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> getRentHistoryForCurrentUser() {
        return rentService.getRentHistoryForCurrentUser();
    }

    @GetMapping("/TransportHistory/{transportId}")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<?> getRentHistoryForTransport(@PathVariable Long transportId) {
        return rentService.getRentHistoryForTransport(transportId);
    }

    @PostMapping("/New/{transportId}")
    @SecurityRequirement(name = "JWT")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> rentTransport(@PathVariable Long transportId, @RequestParam String rentType) {
        return rentService.rentTransport(transportId,rentType);
    }

    @PostMapping("/End/{rentId}")
    @SecurityRequirement(name = "JWT")
    public ResponseEntity<?> endRent(@PathVariable Long rentId, @RequestParam double lat, @RequestParam double lon) {
       return rentService.endRent(rentId, lat, lon);
    }
}
