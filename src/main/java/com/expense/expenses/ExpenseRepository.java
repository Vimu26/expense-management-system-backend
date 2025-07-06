package com.expense.expenses;

import com.expense.expenses.enums.ExpenseType;
import com.expense.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    Page<Expense> findByUser(User user, Pageable pageable);

    Optional<Expense> findByIdAndUser(Long id, User user);

    @Query("""
    SELECT e FROM Expense e
    WHERE (:user_id IS NULL OR e.user.id = :user_id)
    AND (:type_id IS NULL OR e.type.id = :type_id)
    AND (:type_id IS NULL OR e.type.id = :type_id)
    AND (:startDate IS NULL OR e.date >= :startDate)
    AND (:endDate IS NULL OR e.date <= :endDate)
    AND (:month IS NULL OR FUNCTION('MONTH', e.date) = :month)
    AND (:year IS NULL OR FUNCTION('YEAR', e.date) = :year)
""")
    Page<Expense> findFiltered(
            @Param("type_id") Long type_id,
            @Param("user_id") Integer user_id,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("month") Integer month,
            @Param("year") Integer year,
            Pageable pageable
    );
}
