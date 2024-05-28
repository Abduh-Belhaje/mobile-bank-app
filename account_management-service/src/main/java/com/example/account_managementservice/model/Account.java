package com.example.account_managementservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_sequence")
    @SequenceGenerator(name = "account_sequence", sequenceName = "account_sequence",initialValue = 18759632, allocationSize = 1)
    private Long account_nb;

    private double balance;

    @Column(nullable = false , unique = true )
    private String holder_email;

    @Enumerated(EnumType.STRING)
    private Type type;

    private Date openning_date;

    @Column(nullable = true)
    private Date closing_date;

    @Enumerated(EnumType.STRING)
    private Status status;


}
