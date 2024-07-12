package com.momo.sodamachine.controller;

import com.momo.sodamachine.model.dto.response.ProductResponseDto;
import com.momo.sodamachine.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public ResponseEntity<Object> getProducts() {
        List<ProductResponseDto> products = productService.getProducts();
        return ResponseEntity.ok(products);
    }
}
