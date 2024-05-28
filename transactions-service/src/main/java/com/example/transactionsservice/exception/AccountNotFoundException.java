package com.example.transactionsservice.exception;

public class AccountNotFoundException extends Exception{

    public AccountNotFoundException(String msg){
        super(msg);
    }
}
