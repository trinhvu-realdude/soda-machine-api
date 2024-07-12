package com.momo.sodamachine.model.dto.request;

import lombok.Data;

@Data
public class CancelTransactionRequestDto {
    private Integer transactionId;
    private String username;
}
