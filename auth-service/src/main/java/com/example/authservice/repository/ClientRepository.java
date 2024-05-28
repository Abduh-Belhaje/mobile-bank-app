package com.example.authservice.repository;

import com.example.authservice.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Client c WHERE c.c_email = :email")
    boolean existsByEmail(@Param("email") String email);
    @Query("SELECT c FROM Client c WHERE c.c_email = :email")
    Optional<Client> findClientByEmail(@Param("email") String email);

}
