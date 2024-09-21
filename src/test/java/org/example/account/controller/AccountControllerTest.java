package org.example.account.controller;

import org.example.account.dto.Account;
import org.example.account.repository.AccountRepository;
import org.example.account.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Mockito를 이용한 테스트
 */
@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {


    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    private Account testAccount;

    @BeforeEach
    void setUp() {
        testAccount = new Account("testUser", "1000000001", 0L);
    }

    @Test
    @DisplayName("계좌 조회")
    public void createAccountTest() {

        // Given
        given(accountRepository.findByUserId(anyString()))
                .willReturn(List.of());
        given(accountRepository.save(any(Account.class)))
                .willReturn(testAccount);

        // When
        Account createdAccount = accountService.createAccount("testUser", 0L);

        // Then
        assertEquals("testUser", createdAccount.getUserId());
        assertEquals("1000000001", createdAccount.getAccountNumber());
        assertEquals(0L, createdAccount.getBalance());

    }

    @Test
    @DisplayName("계좌 삭제")
    void deleteAccountSuccess() {
        // Given
        given(accountRepository.findByUserId(anyString())) // accountRepository.findByUserId() 호출 시 계좌 리스트 반환
                .willReturn(List.of(testAccount));

        given(accountRepository.findByAccountNumber(anyString())) // accountRepository.findByAccountNumber() 호출 시 testAccount 반환
                .willReturn(Optional.of(testAccount));

        given(accountRepository.save(any(Account.class))) // accountRepository.save() 호출 시 testAccount 반환
                .willReturn(testAccount);

        // When : 계좌 삭제 테스트
        Account deletedAccount = accountService.deleteAccount("testUser", "1000000001");

        // Then : 계좌 삭제 후 검증
        assertFalse(deletedAccount.isStatus());  // 계좌 상태가 비활성화되었는지 검증
        assertNotNull(deletedAccount.getDeletedAt());  // 계좌 삭제 시간이 설정되었는지 검증
    }
}
