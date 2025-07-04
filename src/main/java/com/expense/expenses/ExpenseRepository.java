package com.expense.expenses;

import com.expense.expenses.enums.ExpenseType;
import com.expense.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    Page<Expense> findByUser(User user, Pageable pageable);
    Optional<Expense> findByIdAndUser(Long id, User user);
    Page<Expense> findFiltered(User user, ExpenseType type,
                               LocalDate startDate, LocalDate endDate,
                               Integer month, Integer year,
                               Pageable pageable);
}
