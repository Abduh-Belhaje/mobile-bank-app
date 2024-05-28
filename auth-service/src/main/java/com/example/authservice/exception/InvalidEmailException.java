package com.example.authservice.exception;


import lombok.Getter;


public class InvalidEmailException extends Exception{

    public InvalidEmailException(String mssg){
        super(mssg);
    }

}
