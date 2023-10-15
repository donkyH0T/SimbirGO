package com.example.simbirgo.controllers;

import com.example.simbirgo.security.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/Payment/Hesoyam")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @PostMapping("/{accountId}")
    public ResponseEntity<?> addmoney(@PathVariable Long accountId){
        return paymentService.getMoney(accountId);
    }
}
