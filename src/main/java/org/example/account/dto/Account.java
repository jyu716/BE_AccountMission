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
public class Account {

    @Id
    @GeneratedValue
    private int id;

    private String userId;

    @Column(unique = true)
    private String accountNumber;

    private Long balance;

    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;

    private boolean status;

    public Account() {

    }

    public Account(String userId, String accountNumber, Long balance) {
        this.userId = userId;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.createdAt = LocalDateTime.now();
        this.status = true;
    }

    public void deactivate(){
        this.deletedAt = LocalDateTime.now();
        this.status = false;
    }

    public void usedSuccess(Long amount){
        balance -= amount;
    }

    public void usedCancel(Long amount){
        balance += amount;
    }

}
