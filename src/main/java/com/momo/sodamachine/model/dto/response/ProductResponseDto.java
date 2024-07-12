package com.momo.sodamachine.model.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductResponseDto {
    private Integer productId;
    private String name;
    private Double price;
    private Integer quantity;
    private String imageUrl;
}
