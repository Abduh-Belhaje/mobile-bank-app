package com.example.authservice.exception;


import lombok.Getter;

@Getter
public class EmailAlreadyExistsException extends Exception{

    private String arg;
    public EmailAlreadyExistsException(String mssg,String email){
        super(mssg);
        arg = email;
    }

}
