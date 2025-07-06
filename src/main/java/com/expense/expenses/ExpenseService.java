package com.expense.expenses;

import com.expense.expenseTypes.ExpenseTypes;
import com.expense.expenseTypes.ExpenseTypesRepository;
import com.expense.expenses.dtos.ExpenseRequest;
import com.expense.expenses.dtos.ExpenseResponse;
import com.expense.expenses.enums.ExpenseType;
import com.expense.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final ExpenseTypesRepository expenseTypesRepository;


    public ExpenseResponse createExpense(User user, ExpenseRequest request) {
        ExpenseTypes type = expenseTypesRepository.findById(request.getType())
                .orElseThrow(() -> new RuntimeException("Expense type not found"));

        Expense expense = Expense.builder()
                .amount(request.getAmount())
                .description(request.getDescription())
                .date(request.getDate())
                .type(type)
                .user(user)
                .build();
        Expense saved = expenseRepository.save(expense);
        return toResponse(saved);
    }

    public Page<ExpenseResponse> getExpenses(User user, Pageable pageable) {
        return expenseRepository.findByUser(user, pageable)
                .map(this::toResponse);
    }

    public Page<ExpenseResponse> getFilteredExpenses(Long type_id,
                                                     Integer user_id,
                                                     LocalDate startDate,
                                                     LocalDate endDate,
                                                     Integer month,
                                                     Integer year,
                                                     Pageable pageable) {
        return expenseRepository.findFiltered(type_id, user_id, startDate, endDate, month, year, pageable)
                .map(this::toResponse);
    }


    public ExpenseResponse getExpenseById(User user, Long id) {
        Expense expense1 = expenseRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("Expense not found"));
        return toResponse(expense1);
    }

    public ExpenseResponse updateExpense(User user,Long id, ExpenseRequest request) {
       Expense expenseData = expenseRepository.findById(id).orElseThrow(() -> new RuntimeException("Expense not found"));
        ExpenseTypes type = expenseTypesRepository.findById(request.getType())
                .orElseThrow(() -> new RuntimeException("Expense type not found"));
       expenseData.setAmount(request.getAmount());
       expenseData.setDescription(request.getDescription());
       expenseData.setDate(request.getDate());
       expenseData.setType(type);
       expenseData.setUser(user);

       return toResponse(expenseRepository.save(expenseData));
    }

    public void deleteExpenseById(User user, Long id) {
        Expense expense = expenseRepository.findById(id).orElseThrow(() -> new RuntimeException("Expense not found"));
        expenseRepository.deleteById(id);
    }

    private ExpenseResponse toResponse(Expense expense) {
        ExpenseResponse res = new ExpenseResponse();
        res.setId(expense.getId());
        res.setAmount(expense.getAmount());
        res.setDescription(expense.getDescription());
        res.setDate(expense.getDate());

        if (expense.getType() != null) {
            res.setTypeId(expense.getType().getId());
            res.setTypeName(expense.getType().getName());
        }

        if (expense.getUser() != null) {
            res.setUserId(expense.getUser().getId());
            res.setUserName(expense.getUser().getFirstName() + " " + expense.getUser().getLastName());
        }

        return res;
    }
}
