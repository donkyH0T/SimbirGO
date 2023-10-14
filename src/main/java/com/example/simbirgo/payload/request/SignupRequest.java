package com.example.simbirgo.payload.request;

import lombok.Data;

import java.util.Set;


@Data
public class SignupRequest {
    private String username;
    private String password;
}
