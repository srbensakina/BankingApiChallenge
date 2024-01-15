package com.chanllenge.bankingapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TransferHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "source_account_id")
    private BankAccount sourceAccount;

    @ManyToOne
    @JoinColumn(name = "destination_account_id")
    private BankAccount destinationAccount;

    private Float amount;

    private LocalDateTime transferDateTime;
}