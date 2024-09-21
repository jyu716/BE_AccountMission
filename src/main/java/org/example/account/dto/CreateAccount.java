package org.example.account.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

public class CreateAccount {


    @Getter
    @Setter
    @AllArgsConstructor
    public static class Request {
        @NotNull
        @Min(1)
        private String userId;

        @NotNull
        @Min(0)
        private Long balance;
    }


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private String userId;
        private String accountNumber;
        private LocalDateTime createdAt;

        public static Response from(Account account) {
            return Response.builder()
                    .userId(account.getUserId())
                    .accountNumber(account.getAccountNumber())
                    .createdAt(account.getCreatedAt())
                    .build();
        }
    }

}
