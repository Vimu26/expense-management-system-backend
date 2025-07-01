package com.expense.income;

import com.expense.income.dto.IncomeRequest;
import com.expense.income.dto.IncomeResponse;
import com.expense.income.Income;
import com.expense.income.IncomeRepository;
import com.expense.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IncomeService {
    private final IncomeRepository incomeRepository;

    public IncomeResponse createIncome(User user, IncomeRequest request) {
        Income income = Income.builder()
                .amount(request.getAmount())
                .description(request.getDescription())
                .date(request.getDate())
                .type(request.getType())
                .user(user)
                .build();
        Income saved = incomeRepository.save(income);
        return toResponse(saved);
    }

    public Page<IncomeResponse> getAllUserIncomes(User user, Pageable pageable) {
        return incomeRepository.findByUser(user, pageable)
                .map(this::toResponse);
    }

    public IncomeResponse getIncomeById(User user, Long id) {
        Income income = incomeRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Income not found"));
        return toResponse(income);
    }

    public IncomeResponse updateIncome(User user, Long id, IncomeRequest request) {
        Income income = incomeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Income not found"));

        income.setAmount(request.getAmount());
        income.setDescription(request.getDescription());
        income.setDate(request.getDate());
        income.setType(request.getType());
        income.setUser(user);

        return toResponse(incomeRepository.save(income));
    }

    public void deleteIncome(User user, Long id) {
        Income income = incomeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Income not found"));
        incomeRepository.delete(income);
    }

    private IncomeResponse toResponse(Income income) {
        IncomeResponse res = new IncomeResponse();
        res.setId(income.getId());
        res.setAmount(income.getAmount());
        res.setDescription(income.getDescription());
        res.setDate(income.getDate());
        res.setType(income.getType());
        return res;
    }
}
