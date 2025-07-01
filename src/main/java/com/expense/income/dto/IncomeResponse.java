package com.expense.income.dto;

import com.expense.income.enums.IncomeType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
public class IncomeResponse {
    private Long id;
    private BigDecimal amount;
    private String description;
    private LocalDate date;
    private IncomeType type;
    private Long userId;
    private String userName;

    // Getters
//    public Long getId() {
//        return id;
//    }
//
//    public BigDecimal getAmount() {
//        return amount;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public LocalDate getDate() {
//        return date;
//    }
//
//    public IncomeType getType() {
//        return type;
//    }
//
//    public Long getUserId() {
//        return userId;
//    }
//
//    public String getUserName() {
//        return userName;
//    }
//
//    // Setters
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public void setAmount(BigDecimal amount) {
//        this.amount = amount;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public void setDate(LocalDate date) {
//        this.date = date;
//    }
//
//    public void setType(IncomeType type) {
//        this.type = type;
//    }
//
//    public void setUserId(Long userId) {
//        this.userId = userId;
//    }
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }
}
