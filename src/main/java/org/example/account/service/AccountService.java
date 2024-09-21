package org.example.account.service;

import jakarta.transaction.Transactional;
import org.example.account.dto.Account;
import org.example.account.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // 계좌 생성
    @Transactional
    public Account createAccount(String userId, Long balance) {
        List<Account> accounts = accountRepository.findByUserId(userId);

        if(userId.isEmpty()){
            throw new IllegalArgumentException("사용자를 확인해 주세요.");
        }

        if(accounts.size() >= 10){
            throw new IllegalArgumentException("계좌의 최대 10개까지 생성 가능합니다.");
        }

        String accountNumber = getRandomAccountNumber(accounts);

        // 새로운 Account 객체 생성
        Account account = new Account(userId, accountNumber, balance);

        return accountRepository.save(account);
    }

    //계좌 해지
    @Transactional
    public Account deleteAccount(String userId, String accountNumber) {
        List<Account> accounts = accountRepository.findByUserId(userId); // 아이디의 계좌들 조회
        Optional<Account> getAccount = accountRepository.findByAccountNumber(accountNumber); // accountNumber의 계좌 조회
        Account account = getAccount.get();

        System.out.println("userid::"+userId+", accountNumber::"+accountNumber);

        if (getAccount.isEmpty()) {
            throw new IllegalStateException("해당 계좌를 찾을 수 없습니다.");
        }

        if(accounts.isEmpty()){
            throw new IllegalStateException("사용자가 없습니다.");
        }


        // 사용자 아이디와 계좌 소유주가 다른경우
        if(!account.getUserId().equals(userId)){
            throw new IllegalStateException("사용자 아이디가 일치하지 않습니다.");
        }

        // 계좌가 이미 해지 상태인 경우
        if(!account.isStatus()){
            throw new IllegalStateException("계좌가 이미 해지 상태 입니다.");
        }

        // 잔액이 있는 경우 실패 응답
        if(account.getBalance() > 0){
            throw new IllegalStateException("잔액이 존재할 경우 계좌를 해지할 수 없습니다.");
        }

        account.deactivate();
        return accountRepository.save(account);
    }

    //계좌 리스트 가져오기
    public List<Account> getAccounts(String userId) {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return accountRepository.findByUserId(userId);
    }

    //계좌번호 중복 체크
    public String getRandomAccountNumber(List<Account> accounts) {
        Random random = new Random();
        String accountNumber = String.format("%010d", Math.abs(random.nextLong() % 10000000000L));;

        // 중복되지 않는 계좌 번호를 찾을 때까지 반복
        for(int i = 0; i < accounts.size(); i++ ){
            if(accounts.get(i).getAccountNumber().equals(accountNumber)){
                accountNumber = String.format("%010d", Math.abs(random.nextLong() % 10000000000L));
                i = 0;
            }
        }

        return accountNumber;
    }
}
