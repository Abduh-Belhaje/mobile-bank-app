package com.example.authservice.service;

import com.example.authservice.dto.JwtResponse;
import com.example.authservice.dto.SigninRequest;
import com.example.authservice.dto.SignupRequest;
import com.example.authservice.exception.AuthenticationFailedException;
import com.example.authservice.exception.EmailAlreadyExistsException;
import com.example.authservice.exception.InvalidEmailException;
import com.example.authservice.exception.MessageSendingException;
import com.example.authservice.model.Client;

import java.util.Optional;

public interface AuthService {

    JwtResponse Signin(SigninRequest request) throws InvalidEmailException, AuthenticationFailedException;

    JwtResponse Signup(SignupRequest request) throws EmailAlreadyExistsException;

    boolean checkEmail(String email);

    Client getClient(String email) throws InvalidEmailException;

    int generateRandomNumber(String phone) throws MessageSendingException;
}
