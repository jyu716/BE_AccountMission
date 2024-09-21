package org.example.account.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Transaction {

    @Id
    @GeneratedValue
    private long id;

    private String userId;

    private String accountNumber;

    @Column(unique = true)
    private String transactionId; // 거래ID

    private Long amount; // 거래금액

    private String transaction_result; // 거래성공여부

    private LocalDateTime transactedAt; // 거래일시

    public Transaction() {

    }

    public Transaction(String userId, String accountNumber, String transactionId, Long amount, String transaction_result) {
        this.userId = userId;
        this.accountNumber = accountNumber;
        this.transactionId = transactionId;
        this.amount = amount;
        this.transaction_result = transaction_result;
        this.transactedAt = LocalDateTime.now();
    }
}
