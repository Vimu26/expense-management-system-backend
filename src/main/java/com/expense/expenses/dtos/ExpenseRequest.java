package com.expense.expenses.dtos;

import com.expense.expenses.enums.ExpenseType;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class ExpenseRequest {
    @NotNull
    private BigDecimal amount;
    private String description;
    private LocalDate date;
    private Long type;
}
