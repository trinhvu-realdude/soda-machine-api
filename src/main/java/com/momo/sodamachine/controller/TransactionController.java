package com.momo.sodamachine.controller;

import com.momo.sodamachine.model.dto.request.CancelTransactionRequestDto;
import com.momo.sodamachine.model.dto.request.TransactionRequestDto;
import com.momo.sodamachine.model.dto.response.TransactionResponseDto;
import com.momo.sodamachine.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @RequestMapping(value = "/createTransaction", method = RequestMethod.POST)
    public ResponseEntity<Object> createTransaction(@RequestBody TransactionRequestDto transactionRequest) {
        TransactionResponseDto transaction = transactionService.createTransaction(transactionRequest);
        return ResponseEntity.ok(transaction);
    }

    @RequestMapping(value = "/cancelTransaction", method = RequestMethod.PUT)
    public ResponseEntity<Object> cancelTransaction(@RequestBody CancelTransactionRequestDto cancelTransactionRequestDto) {
        TransactionResponseDto refund = transactionService.cancelTransaction(cancelTransactionRequestDto);
        return ResponseEntity.ok(refund);
    }
}
