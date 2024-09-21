package org.example.account.service;

import jakarta.transaction.Transactional;
import org.example.account.TransactionType;
import org.example.account.dto.Account;
import org.example.account.dto.Transaction;
import org.example.account.repository.AccountRepository;
import org.example.account.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    //거래 사용
    @Transactional
    public Transaction useTransaction(String userId, String accountNumber, Long amount) {
        List<Account> getAccountList = accountRepository.findByUserId(userId);
        Optional<Account> getAccount = accountRepository.findByAccountNumber(accountNumber);
        Account account = getAccount.get();
        String uuid = UUID.randomUUID().toString().replace("-", "");


        //사용자 없는 경우,
        if(getAccountList.isEmpty()) {
            throw new IllegalStateException("사용자가 존재하지 않습니다.");
        }


        // 사용자 아이디와 계좌 소유주가 다른 경우,
        if(!account.getUserId().equals(userId)){
            throw new IllegalStateException("사용자 아이디가 일치하지 않습니다.");
        }

        // 계좌가 이미 해지 상태인 경우,
        if(!account.isStatus()){
            throw new IllegalStateException("계좌가 이미 해지 상태 입니다.");
        }

        // 거래금액이 잔액보다 큰 경우,
        if(account.getBalance() < amount){
            throw new IllegalStateException("한도초과 입니다.");
        }

        // 거래금액이 너무 작거나 큰 경우 실패 응답
        if(amount < 0 || amount > 1_000_000_000){
            throw new IllegalStateException("거래 금액을 확인해 주세요.");
        }

        // 사용 저장
        Transaction transaction = new Transaction(userId, accountNumber, uuid, amount, TransactionType.USED.toString());

        // 계좌 잔액 변경
        account.usedSuccess(amount);

        accountRepository.save(account);
        return transactionRepository.save(transaction);
    }


    //거래 취소
    @Transactional
    public Transaction cancelTransaction(String accountNumber, String transactionId, Long amount) {
        Transaction getTransaction = transactionRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new IllegalStateException("해당 거래를 찾을 수 없습니다."));

        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalStateException("해당 계좌를 찾을 수 없습니다."));

        String uuid = UUID.randomUUID().toString().replace("-", "");

        //원거래 금액과 취소 금액이 다른 경우,
        if(!getTransaction.getAmount().equals(amount)){
            throw new IllegalStateException("취소 금액이 일치하지 않습니다.");
        }

        // 트랜잭션이 해당 계좌의 거래가 아닌경우 실패 응답
        if(!getTransaction.getAccountNumber().equals(accountNumber)){
            throw new IllegalStateException("해당 계좌의 거래가 아닙니다.");
        }

        Transaction transaction = new Transaction(getTransaction.getUserId(),
                accountNumber,
                uuid,
                amount,
                TransactionType.CANCEL.toString());

        account.usedCancel(amount);

        accountRepository.save(account);
        return transactionRepository.save(transaction);
    }


    //거래 확인
    @Transactional
    public Transaction getTransaction(String transactionId) {
        Transaction getTransaction = transactionRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new IllegalStateException("해당 거래를 찾을 수 없습니다."));

        return getTransaction;
    }


}
