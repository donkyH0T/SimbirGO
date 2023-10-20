package com.example.simbirgo.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class UpdateRequest {
    private String username;

    public UpdateRequest() {
    }

    public UpdateRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    private String password;
}
