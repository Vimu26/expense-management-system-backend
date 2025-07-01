package com.expense.income.dto;

import com.expense.income.enums.IncomeType;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
public class IncomeRequest {
    // Setters
    // Getters
    @NotNull
    private BigDecimal amount;

    private String description;

    private LocalDate date;

    private IncomeType type;

}
