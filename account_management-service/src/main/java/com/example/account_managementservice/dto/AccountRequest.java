package com.example.account_managementservice.dto;

import com.example.account_managementservice.model.Status;
import com.example.account_managementservice.model.Type;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class AccountRequest {

    private double balance;

    private String holder_email;
}
