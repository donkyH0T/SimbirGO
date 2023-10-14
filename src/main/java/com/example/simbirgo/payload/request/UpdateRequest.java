package com.example.simbirgo.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateRequest {
    private String username;
    private String password;
}
