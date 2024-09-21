package org.example.account.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

public class CancelTransaction {


    @Getter
    @Setter
    @AllArgsConstructor
    public static class Request {
        @NotNull
        @Min(1)
        private String userId;

        private String accountNumber;

        private String transactionId;

        @NotNull
        @Min(0)
        private Long amount;
    }


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        //계좌번호, transaction_result, transaction_id, 거래금액, 거래일시
        private String accountNumber;
        private String transaction_result;
        private String transactionId;
        private Long amount;
        private LocalDateTime transacionAt;

        public static CancelTransaction.Response from(Transaction transaction) {
            return Response.builder()
                    .accountNumber(transaction.getAccountNumber())
                    .transaction_result(transaction.getTransaction_result())
                    .transactionId(transaction.getTransactionId())
                    .amount(transaction.getAmount())
                    .transacionAt(transaction.getTransactedAt())
                    .build();
        }
    }

}
