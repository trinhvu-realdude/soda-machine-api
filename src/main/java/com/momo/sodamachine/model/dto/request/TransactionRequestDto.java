package com.momo.sodamachine.model.dto.request;

import lombok.Data;

@Data
public class TransactionRequestDto {
    private Integer productId;
    private Double price;
    private String username;
}
