package com.example.simbirgo.repository;


import com.example.simbirgo.entity.Transport;
import com.example.simbirgo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	@Query(value = "SELECT * FROM users LIMIT :start, :count", nativeQuery = true)
	List<User> findAllUsers(@Param("start") int start, @Param("count") int count);



}
