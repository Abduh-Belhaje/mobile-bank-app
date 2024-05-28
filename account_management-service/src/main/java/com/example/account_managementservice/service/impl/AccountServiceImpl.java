package com.example.account_managementservice.service.impl;

import com.example.account_managementservice.dto.AccountRequest;
import com.example.account_managementservice.dto.UpdateBalanceRequest;
import com.example.account_managementservice.exception.AccountAlreadyExistException;
import com.example.account_managementservice.exception.AccountNotFoundException;
import com.example.account_managementservice.model.Account;
import com.example.account_managementservice.model.Status;
import com.example.account_managementservice.model.Type;
import com.example.account_managementservice.repository.AccountRepository;
import com.example.account_managementservice.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    @Override
    public void createAccount(AccountRequest request) throws AccountAlreadyExistException {
        boolean exist = accountRepository.existsByEmail(request.getHolder_email());

        if(!exist){
            Account account = new Account();
            account.setBalance(request.getBalance());
            account.setHolder_email(request.getHolder_email());
            account.setType(Type.individually);
            account.setStatus(Status.ACTIVE);
            account.setOpenning_date(new Date());

            accountRepository.save(account);

        }else {
            throw new AccountAlreadyExistException("An account Already exist with : " + request.getHolder_email());
        }
    }

    @Override
    public void updateBalance(UpdateBalanceRequest request) throws AccountNotFoundException {
        Account account = accountRepository.findById(request.getAccount_nb())
                .orElseThrow(()-> new AccountNotFoundException("Account with number: " + request.getAccount_nb() + " doesn't exist"));

        account.setBalance(request.getBalance());
        accountRepository.save(account);
    }


    @Override
    public void deleteAccount(Long accountNb) throws AccountNotFoundException {
        boolean exist = accountRepository.existsById(accountNb);
        if(exist){
            accountRepository.deleteById(accountNb);
        }else{
            throw new AccountNotFoundException("Account with number: " + accountNb + " doesn't exist");
        }
    }

    @Override
    public Account getAccount(Long accountNb) throws AccountNotFoundException {

        return accountRepository.findById(accountNb)
                .orElseThrow(()-> new AccountNotFoundException("Account with number: " + accountNb + " doesn't exist"));
    }

    @Override
    public Account getAccountByEmail(String email) throws AccountNotFoundException {
        return accountRepository.findAccountByEmail(email)
                .orElseThrow(()-> new AccountNotFoundException("Account with email: " + email + " doesn't exist"));
    }

    @Override
    public double getAccountBalance(Long account_nb) throws AccountNotFoundException {
        boolean exist = accountRepository.existsById(account_nb);

        if(exist){
            return accountRepository.getAccountBalance(account_nb);
        }
        throw new AccountNotFoundException("Account with number: " + account_nb + " doesn't exist");
    }


    @Override
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }
}
