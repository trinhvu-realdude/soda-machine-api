package com.momo.sodamachine.service;

import com.momo.sodamachine.model.Product;
import com.momo.sodamachine.model.Promotion;
import com.momo.sodamachine.model.Transaction;
import com.momo.sodamachine.model.User;
import com.momo.sodamachine.model.dto.request.CancelTransactionRequestDto;
import com.momo.sodamachine.model.dto.request.TransactionRequestDto;
import com.momo.sodamachine.model.dto.response.TransactionResponseDto;
import com.momo.sodamachine.repository.ProductRepository;
import com.momo.sodamachine.repository.PromotionRepository;
import com.momo.sodamachine.repository.TransactionRepository;
import com.momo.sodamachine.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Service
public class TransactionService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PromotionRepository promotionRepository;

    @Transactional
    public TransactionResponseDto createTransaction(TransactionRequestDto transactionRequest) {
        Product product = productRepository.findById(transactionRequest.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getQuantity() < 1) {
            throw new RuntimeException("Not enough quantity available");
        }

        product.setQuantity(product.getQuantity() - 1);
        productRepository.save(product);

        User user = userRepository.findByUsername(transactionRequest.getUsername());

        if (user == null) {
            user = new User();
            user.setUsername(transactionRequest.getUsername());
            userRepository.save(user);
        }

        Integer checkConsecutivePurchases = transactionRepository.checkConsecutivePurchases(user, product);

        Promotion promotion = promotionRepository.findById(1).orElseThrow(() -> new RuntimeException("Promotion not found"));
        Double winRate = promotion.getWinRate() / 100;
        Double remainingBudget = promotion.getRemainingBudget();
        boolean isWin = isWin(winRate);

        // Check if user purchases 3 products consecutively, luckily gets 10% chance and the limit still does not reach
        if (checkConsecutivePurchases >= 3 && isWin && (remainingBudget > 0 && remainingBudget <= 50000)) {
            // Update remaining_budget = remaining_budget - product.price
            promotion.setRemainingBudget(promotion.getRemainingBudget() - product.getPrice());
            promotionRepository.save(promotion);
        } else {
            isWin = false;
        }

        Double totalAmount = transactionRequest.getPrice();

        Transaction transaction = new Transaction();
        transaction.setProduct(product);
        transaction.setUser(user);
        transaction.setAmountPaid(totalAmount);
        transaction.setStatus("COMPLETED");
        transaction.setTransactionTime(new Date());
        transactionRepository.save(transaction);

        return TransactionResponseDto.builder()
                .transactionId(transaction.getTransactionId())
                .userId(user.getUserId())
                .amountPaid(transaction.getAmountPaid())
                .quantity(isWin ? 2 : 1)
                .status(transaction.getStatus())
                .transactionTime(transaction.getTransactionTime())
                .isWin(isWin)
                .build();
    }

    @Transactional
    public TransactionResponseDto cancelTransaction(CancelTransactionRequestDto cancelTransactionRequestDto) {
        Transaction transaction = transactionRepository.findById(cancelTransactionRequestDto.getTransactionId())
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        User user = userRepository.findByUsername(cancelTransactionRequestDto.getUsername());

        if (!transaction.getStatus().equals("COMPLETED") || user == null) {
            throw new RuntimeException("Transaction cannot be canceled");
        }

        Product product = transaction.getProduct();
        product.setQuantity(product.getQuantity() + 1);
        productRepository.save(product);

        transaction.setStatus("CANCELED");
        transactionRepository.save(transaction);

        return TransactionResponseDto.builder()
                .transactionId(transaction.getTransactionId())
                .userId(user.getUserId())
                .amountPaid(transaction.getAmountPaid())
                .status(transaction.getStatus())
                .transactionTime(transaction.getTransactionTime())
                .build();
    }

    private boolean isWin(Double winRate) {
        Random random = new Random();
        double randomValue = random.nextDouble();
        return randomValue < winRate;
    }
}
