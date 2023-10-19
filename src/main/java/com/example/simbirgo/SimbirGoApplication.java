package com.example.simbirgo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class SimbirGoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimbirGoApplication.class, args);
    }

}
