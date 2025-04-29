package com.whales.eplant.data.repository;

import com.whales.eplant.data.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    boolean existsByEmail(String email);

    Optional<Users> findByEmail(String email);
    @Query("SELECT u FROM Users u LEFT JOIN FETCH u.vendors WHERE u.email = :email")
    Optional<Users> findByEmailWithVendors(@Param("email") String email);
//    Optional<Users> findB(String email);
}
