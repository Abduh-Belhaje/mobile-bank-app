package com.example.transactionsservice.repository;

import com.example.transactionsservice.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OperaRepository extends JpaRepository<Operation,Long> {

    @Query("SELECT o FROM Operation o WHERE o.account_nb = :account_nb")
    List<Operation> findOptByAccountNb(@Param("account_nb") Long account_nb);
}
