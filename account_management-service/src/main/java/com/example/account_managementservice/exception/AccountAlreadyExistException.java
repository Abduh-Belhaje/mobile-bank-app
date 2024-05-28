package com.example.account_managementservice.exception;

public class AccountAlreadyExistException extends Exception{
    public AccountAlreadyExistException(String mssg){
        super(mssg);
    }
}
