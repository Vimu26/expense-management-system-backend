package com.expense.expenses;

import com.expense.expenseTypes.ExpenseTypes;
import com.expense.expenses.enums.ExpenseType;
import com.expense.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "expense")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Expense {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount;

    private String description;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expense_type_id")
    private ExpenseTypes type;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // references User.id
    private User user;
}
