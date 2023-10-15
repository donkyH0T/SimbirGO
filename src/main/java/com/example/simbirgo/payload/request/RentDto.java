package com.example.simbirgo.payload.request;

import lombok.Data;

import javax.persistence.Column;

@Data
public class RentDto {
    private Long transportId;
    private Long userId;
    private String timeStart;
    private String timeEnd;
    private Double priceOfUnit;
    private String priceType;
    private Double finalPrice;
}
