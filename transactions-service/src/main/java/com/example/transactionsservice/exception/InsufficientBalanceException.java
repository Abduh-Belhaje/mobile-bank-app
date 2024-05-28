package com.example.transactionsservice.exception;

public class InsufficientBalanceException extends Exception{

    public InsufficientBalanceException(String mssg){
        super(mssg);
    }
}
