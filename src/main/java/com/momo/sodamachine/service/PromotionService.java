package com.momo.sodamachine.service;

import com.momo.sodamachine.model.Promotion;
import com.momo.sodamachine.repository.PromotionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    public void adjustWinRateIfBudgetNotReached() {
        try {
            log.info("Checking limited budget for a day.");

            Promotion promotion = promotionRepository.findById(1).orElseThrow(() -> new RuntimeException("Promotion not found"));

            Double dailyBudget = promotion.getDailyBudget();
            Double remainingBudget = promotion.getRemainingBudget();

            if (remainingBudget != 0) {
                Double winRateIncreaseFactor = promotion.getWinRate() * 0.5;
                promotion.setWinRate(promotion.getWinRate() + winRateIncreaseFactor);
                log.info("Daily budget not reached. Increased win rate for next day by 50%.");
            }
            promotion.setRemainingBudget(dailyBudget);
            promotionRepository.save(promotion);
        } catch (Exception e) {
            log.error("Error: " + e.getMessage());
        }
    }
}
