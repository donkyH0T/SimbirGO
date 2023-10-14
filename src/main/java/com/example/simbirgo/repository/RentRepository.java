package com.example.simbirgo.repository;

import com.example.simbirgo.entity.Rent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentRepository extends JpaRepository<Rent,Long> {
    List<Rent> findRentsByUserId(Long userId);
    List<Rent> findRentsByTransportId(Long id);
}
