package com.expense.income;

import com.expense.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IncomeRepository extends JpaRepository<Income, Long> {
    Page<Income> findByUser(User user, Pageable pageable);
    Optional<Income> findByIdAndUser(Long id, User user);
}
