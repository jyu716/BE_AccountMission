package org.example.account.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

public class DeleteAccount {


    @Getter
    @Setter
    @AllArgsConstructor
    public static class Request {
        @NotNull
        @Min(1)
        private String userId;

        @NotNull
        private String accountNumber;
    }


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private String userId;
        private String accountNumber;
        private LocalDateTime deletedAt;

        public static DeleteAccount.Response from(Account account) {
            return Response.builder()
                    .userId(account.getUserId())
                    .accountNumber(account.getAccountNumber())
                    .deletedAt(account.getDeletedAt())
                    .build();
        }
    }

}
