package org.example.account.controller;

import lombok.RequiredArgsConstructor;
import org.example.account.dto.CancelTransaction;
import org.example.account.dto.CreateTransaction;
import org.example.account.dto.Transaction;
import org.example.account.service.TransactionService;
import org.springframework.web.bind.annotation.*;

@RestController // RESTful 웹 서비스를 개발시 사용
@RequiredArgsConstructor // 의존성 주입
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/transaction/use")
    public CreateTransaction.Response createTransaction(@RequestBody CreateTransaction.Request request) {

        return CreateTransaction.Response.from(
                transactionService.useTransaction(
                        request.getUserId(),
                        request.getAccountNumber(),
                        request.getAmount())
        );
    }

    @PostMapping("/transaction/cancelUse")
    public CancelTransaction.Response cancelTransaction(@RequestBody CancelTransaction.Request request) {
        return CancelTransaction.Response.from(
                transactionService.cancelTransaction(
                        request.getAccountNumber(),
                        request.getTransactionId(),
                        request.getAmount()
                )
        );
    }

    @GetMapping("/transaction/getTransaction")
    public Transaction getTransaction(@RequestParam String transactionId) {
        return transactionService.getTransaction(transactionId);
    }
}
