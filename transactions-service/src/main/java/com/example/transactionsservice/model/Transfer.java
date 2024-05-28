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
public class Transfer {

    @Id
    @GeneratedValue
    private Long trans_id;
    private double amount;
    @Column(nullable = false)
    private Long sender_acc;
    @Column(nullable = false)
    private Long recipient_acc;
    private Date operation_date;

}
