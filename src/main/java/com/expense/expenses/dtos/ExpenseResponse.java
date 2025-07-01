package com.expense.expenses.dtos;

import com.expense.expenses.enums.ExpenseType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class ExpenseResponse {
    private Long id;
    private BigDecimal amount;
    private String description;
    private LocalDate date;
    private ExpenseType type;

}
