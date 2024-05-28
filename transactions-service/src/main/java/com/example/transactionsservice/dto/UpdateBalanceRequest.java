package com.example.transactionsservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateBalanceRequest {

    private Long account_nb;

    private double balance;

}