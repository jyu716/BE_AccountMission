package org.example.account.repository;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.example.account.dto.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    // 비관적 락을 사용하여 계좌를 조회할 때 다른 트랜잭션이 해당 계좌에 접근하지 못하도록 설정
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "30000")}) // 락 대기 시간 설정
    Optional<Account> findByAccountNumber(String  accountNumber);
    List<Account> findByUserId(String username);

}
