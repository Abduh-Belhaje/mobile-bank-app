package com.example.account_managementservice.repository;

import com.example.account_managementservice.model.Account;
import com.example.account_managementservice.model.Status;
import com.example.account_managementservice.model.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
class AccountRepositoryTest {

    @Autowired
    private AccountRepository underTest;

    @BeforeEach
    public void setup(){
        Account account = new Account();
        account.setBalance(1000);
        account.setHolder_email("email@exemple.com");
        account.setType(Type.individually);
        account.setStatus(Status.ACTIVE);
        account.setOpenning_date(new Date());
        underTest.save(account);
    }

    @Test
    void shouldFindExistingAccountByEmail() {

        String email = "email@exemple.com";

        Optional<Account> account = underTest.findAccountByEmail(email);
        assertThat(account).isPresent();

    }

    @Test
    void InExistingAccountByEmail(){
        String email = "abel@exemple.com";

        Optional<Account> account = underTest.findAccountByEmail(email);
        assertThat(account).isEmpty();

    }


    @Test
    void AccountShouldExistsByEmail() {
        String email = "email@exemple.com";

        boolean exist = underTest.existsByEmail(email);
        assertThat(exist).isTrue();
    }

    @Test
    void DeleteExistingAccountByEmail() {
        String email = "email@exemple.com";
        // Delete the account
        underTest.deleteAccountByEmail(email);

        // Check if the account has been deleted by trying to find it
        Optional<Account> deletedAccount = underTest.findAccountByEmail(email);

        // Assert that the account is no longer present
        assertThat(deletedAccount).isEmpty();

    }

    @Test
    void shouldGetAccountBalance(){
        String email = "email@exemple.com";

        Optional<Account> account = underTest.findAccountByEmail(email);

        assertThat(account).isPresent();
        double balance = underTest.getAccountBalance(account.get().getAccount_nb());
        assertThat(account.get().getBalance()).isEqualTo(balance);

    }

}