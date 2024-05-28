package com.example.authservice.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignupRequest {
    private String c_firstname;
    private String c_lastname;
    private String c_email;
    private String c_password;
    private String c_phone;
}
