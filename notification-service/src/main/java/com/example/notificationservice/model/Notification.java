package com.example.notificationservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table
public class Notification {

    @Id
    @GeneratedValue
    private Long notif_Id;
    @Column(nullable = false)
    private String rcp_phone;
    private String message;
    private Date date;
}
