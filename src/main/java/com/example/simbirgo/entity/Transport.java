package com.example.simbirgo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "transports")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transport {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false)
   private Boolean canBeRented;
    @Column(nullable = false)
   private String transportType;
    @Column(nullable = false)
   private String model;
    @Column(nullable = false)
   private String color;
    @Column(nullable = false)
   private String identifier;

   private String description;
    @Column(nullable = false)
   private Double latitude;
    @Column(nullable = false)
   private Double longitude;
   private Double minutePrice;
   private Double dayPrice;
}
