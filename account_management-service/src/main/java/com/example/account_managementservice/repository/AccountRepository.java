package com.example.account_managementservice.repository;

import com.example.account_managementservice.model.Account;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {

    @Query("SELECT c FROM Account c WHERE c.holder_email = :email")
    Optional<Account> findAccountByEmail(@Param("email") String email);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Account c WHERE c.holder_email = :email")
    boolean existsByEmail(@Param("email") String email);

    @Query("SELECT c.balance FROM Account c WHERE c.account_nb = ?1")
    double getAccountBalance(Long accountNb);
    @Transactional
    @Modifying
    @Query("DELETE FROM Account a WHERE a.holder_email = ?1")
    void deleteAccountByEmail(String email);
}
