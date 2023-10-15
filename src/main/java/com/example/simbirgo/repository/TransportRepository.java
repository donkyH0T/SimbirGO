package com.example.simbirgo.repository;

import com.example.simbirgo.entity.Transport;
import com.example.simbirgo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransportRepository extends JpaRepository<Transport,Long> {
    @Query(value = "SELECT t FROM transports t " + "WHERE t.transport_type = :transportType " + "AND SQRT(POW(t.latitude - :centerLat, 2) + POW(t.longitude - :centerLon, 2)) <= :radius",nativeQuery = true)
    List<Transport> findTransportByTypeAndWithinRadius(@Param("transportType") String transportType,@Param("centerLat") double centerLat, @Param("centerLon") double centerLon, @Param("radius") double radius);

    @Query(value = "SELECT t FROM transports t WHERE t.transport_type = :transportType LIMIT :start, :count",nativeQuery = true)
    List<Transport> findTransports(@Param("transportType") String transportType, @Param("start") int start, @Param("count") int count);



}
