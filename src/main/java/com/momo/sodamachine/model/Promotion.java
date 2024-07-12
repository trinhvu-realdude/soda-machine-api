package com.momo.sodamachine.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "promotions")
@Data
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promotion_id")
    private Integer promotionId;

    @Column(name = "win_rate")
    private Double winRate;

    @Column(name = "daily_budget")
    private Double dailyBudget;

    @Column(name = "remaining_budget")
    private Double remainingBudget;

}
