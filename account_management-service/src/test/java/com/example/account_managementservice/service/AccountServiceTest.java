package com.example.account_managementservice.service;

import com.example.account_managementservice.dto.AccountRequest;
import com.example.account_managementservice.dto.UpdateBalanceRequest;
import com.example.account_managementservice.exception.AccountAlreadyExistException;
import com.example.account_managementservice.exception.AccountNotFoundException;
import com.example.account_managementservice.model.Account;
import com.example.account_managementservice.model.Status;
import com.example.account_managementservice.model.Type;
import com.example.account_managementservice.repository.AccountRepository;
import com.example.account_managementservice.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
    private AccountService accountService;
    @Mock
    private AccountRepository accountRepository;

    @BeforeEach
    public void setup(){
        accountService = new AccountServiceImpl(accountRepository);
    }

    @Test
    void shouldCreateAccount() throws AccountAlreadyExistException {
        AccountRequest account = new AccountRequest(
                100,
                "email@exemple.com"
        );

        accountService.createAccount(account);

        verify(accountRepository).save(argThat(argument ->
                argument.getBalance() == 100.0 &&
                        argument.getHolder_email().equals("email@exemple.com") &&
                        argument.getType() == Type.individually &&
                        argument.getStatus() == Status.ACTIVE &&
                        argument.getAccount_nb() == null
        ));

    }

    @Test
    void shouldNotCreateAccount() throws AccountAlreadyExistException{
        AccountRequest account = new AccountRequest(
                100,
                "email@exemple.com"
        );

        given(accountRepository.existsByEmail(account.getHolder_email()))
                .willReturn(true);

        assertThatThrownBy(()-> accountService.createAccount(account))
                .isInstanceOf(AccountAlreadyExistException.class)
                .hasMessageContaining("An account Already exist with : " + account.getHolder_email());

    }


    @Test
    void shouldDeleteAccount() throws AccountNotFoundException {
        Long accountNb = 102554588L;

        given(accountRepository.existsById(accountNb))
                .willReturn(true);

        accountService.deleteAccount(accountNb);
        verify(accountRepository).deleteById(accountNb);

    }

    @Test
    void ShouldUpdateBalance() throws AccountNotFoundException {

        UpdateBalanceRequest request = new UpdateBalanceRequest(
                102554588L,
                2000
        );

        Account account = new Account();
        account.setBalance(1000);
        account.setHolder_email("email@exemple.com");
        account.setType(Type.individually);
        account.setStatus(Status.ACTIVE);
        account.setOpenning_date(new Date());

        given(accountRepository.findById(request.getAccount_nb()))
                .willReturn(Optional.of(account));

        accountService.updateBalance(request);
        verify(accountRepository).save(argThat(argument ->
                argument.getBalance() == request.getBalance()
                ));

    }
}