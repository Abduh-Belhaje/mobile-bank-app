package com.example.transactionsservice.dto;

import lombok.Data;
import lombok.Setter;

import java.util.Date;

@Data
@Setter
public class TransResponse {
        String type;
        Date date;
        double amount;
        String from;
        String to;

}
