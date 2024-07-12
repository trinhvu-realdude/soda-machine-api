package com.momo.sodamachine.service;

import com.momo.sodamachine.model.dto.response.ProductResponseDto;
import com.momo.sodamachine.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductResponseDto> getProducts() {
        return productRepository.findAll()
                .stream()
                .map(p -> ProductResponseDto.builder()
                        .productId(p.getProductId())
                        .name(p.getName())
                        .price(p.getPrice())
                        .quantity(p.getQuantity())
                        .imageUrl(p.getImageUrl())
                        .build()
                )
                .toList();
    }
}
