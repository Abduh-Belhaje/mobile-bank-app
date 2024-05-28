package com.example.authservice.exception;

import lombok.Getter;

@Getter
public class AuthenticationFailedException extends Exception{

    public AuthenticationFailedException(String mssg){
        super(mssg);
    }
}
