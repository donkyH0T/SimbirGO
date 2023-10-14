package com.example.simbirgo.repository;

import com.example.simbirgo.entity.Transport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransportRepository extends JpaRepository<Transport,Long> {
    @Query("SELECT t FROM Transport t " + "WHERE t.transportType = :transportType " + "AND SQRT(POW(t.latitude - :centerLat, 2) + POW(t.longitude - :centerLon, 2)) <= :radius")
    List<Transport> findTransportByTypeAndWithinRadius(@Param("transportType") String transportType,@Param("centerLat") double centerLat, @Param("centerLon") double centerLon, @Param("radius") double radius);

}
