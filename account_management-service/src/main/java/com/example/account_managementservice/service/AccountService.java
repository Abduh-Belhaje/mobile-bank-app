package com.example.account_managementservice.service;

import com.example.account_managementservice.dto.AccountRequest;
import com.example.account_managementservice.dto.UpdateBalanceRequest;
import com.example.account_managementservice.exception.AccountAlreadyExistException;
import com.example.account_managementservice.exception.AccountNotFoundException;
import com.example.account_managementservice.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    void createAccount(AccountRequest request) throws AccountAlreadyExistException;

//  void updateAccount(AccountRequest request) throws AccountNotFoundException;
    void deleteAccount(Long accountNb) throws AccountNotFoundException;
    void updateBalance(UpdateBalanceRequest request) throws AccountNotFoundException;
    Account getAccount(Long accountNb)throws AccountNotFoundException;
    Account getAccountByEmail(String email)throws AccountNotFoundException;
    double getAccountBalance(Long account_nb) throws AccountNotFoundException;
    List<Account> getAccounts();

}
