package com.example.transactionsservice.repository;

import com.example.transactionsservice.model.Operation;
import com.example.transactionsservice.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransRepository extends JpaRepository<Transfer,Long> {
    @Query("SELECT o FROM Transfer o WHERE o.sender_acc = :account_nb OR o.recipient_acc = :account_nb")
    List<Transfer> findTransByAccountNb(@Param("account_nb") Long account_nb);
}
