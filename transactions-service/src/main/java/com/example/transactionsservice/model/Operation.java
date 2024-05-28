package com.example.transactionsservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table
public class Operation{

    @Id
    @GeneratedValue
    private Long trans_id;
    private double amount;
    @Column(nullable = false)
    private Long account_nb;
    private Date operation_date;
    @Enumerated(EnumType.STRING)
    private Type type;

}
