package com.expense.expenseTypes;

import com.expense.expenses.Expense;
import com.expense.expenses.enums.ExpenseType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExpenseTypesRepository extends JpaRepository<ExpenseTypes, Long> {
    Optional<ExpenseType> findByName(String name);

    @Query("""
    SELECT et FROM ExpenseTypes et
    WHERE (:userId IS NULL OR et.createdBy.id = :userId)
    AND (:name IS NULL OR LOWER(et.name) LIKE LOWER(CONCAT('%', :name, '%')))
    """)
    Page<ExpenseTypes> findFiltered(
            @Param("userId") Long userId,
            @Param("name") String name,
            Pageable pageable
    );
}


