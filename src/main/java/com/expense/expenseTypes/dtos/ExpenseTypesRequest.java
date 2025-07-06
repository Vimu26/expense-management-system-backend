package com.expense.expenseTypes.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@Getter
@Setter
public class ExpenseTypesRequest {
    @NotNull
    String name;
}
