package com.example.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotifRequest {

    private String phone_number;
    private String mssg;
}
