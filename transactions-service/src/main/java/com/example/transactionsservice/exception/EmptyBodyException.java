package com.example.transactionsservice.exception;

public class EmptyBodyException extends Exception{

    public EmptyBodyException(String msg){
        super(msg);
    }
}
