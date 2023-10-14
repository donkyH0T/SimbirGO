package com.example.simbirgo.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "rent")
public class Rent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private Long transportId;
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private String timeStart;
    private String timeEnd;
    @Column(nullable = false)
    private Double priceOfUnit;
    @Column(nullable = false)
    private String priceType;
    private Double finalPrice;


}
