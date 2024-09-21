package org.example.account.controller;

import lombok.RequiredArgsConstructor;
import org.example.account.dto.AccountInfo;
import org.example.account.service.AccountService;
import org.example.account.dto.CreateAccount;
import org.example.account.dto.DeleteAccount;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController // RESTful 웹 서비스를 개발시 사용
@RequiredArgsConstructor // 의존성 주입
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/account")
    public CreateAccount.Response createAccount(@RequestBody CreateAccount.Request request) {

        return CreateAccount.Response.from(
                accountService.createAccount(
                        request.getUserId(),
                        request.getBalance()
                )
        );
    }

    @DeleteMapping("/account")
    public DeleteAccount.Response closeAccount(@RequestBody DeleteAccount.Request request) {
        return DeleteAccount.Response.from(
                accountService.deleteAccount(
                        request.getUserId(),
                        request.getAccountNumber()
                )
        );
    }

    @GetMapping("/account")
    public List<AccountInfo.Response> getAccount(@RequestParam String userId) {
        return accountService.getAccounts(userId)
                .stream()
                .map(AccountInfo.Response::from) // Account를 AccountInfo.Response로 변환
                .collect(Collectors.toList()); // 리스트로 변환
    }
}