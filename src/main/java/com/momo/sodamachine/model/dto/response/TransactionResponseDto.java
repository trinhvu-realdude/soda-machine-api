package com.momo.sodamachine.model.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class TransactionResponseDto {
    private Integer transactionId;
    private Integer userId;
    private Double amountPaid;
    private Integer quantity;
    private String status;
    private Date transactionTime;
    private Boolean isWin;
}
