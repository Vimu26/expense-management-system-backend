package com.expense.expenseTypes;

import com.expense.expenseTypes.dtos.ExpenseTypesRequest;
import com.expense.expenseTypes.dtos.ExpenseTypesResponse;
import com.expense.expenses.ExpenseRepository;
import com.expense.expenses.enums.ExpenseType;
import com.expense.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseTypesService {
    private final ExpenseTypesRepository expenseTypesRepository;

    public ExpenseTypesResponse createType(ExpenseTypesRequest request, User user) {
        ExpenseTypes type = ExpenseTypes.builder()
                .name(request.getName())
                .createdBy(user)
                .build();

        return toResponse(expenseTypesRepository.save(type));
    }

    public ExpenseTypesResponse updateType(Long id, ExpenseTypesRequest request, User user) {
        ExpenseTypes type = expenseTypesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense type not found"));

        type.setName(request.getName());
        type.setUpdatedBy(user);

        return toResponse(expenseTypesRepository.save(type));
    }

    public List<ExpenseTypesResponse> getAllTypes() {
        return expenseTypesRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public Page<ExpenseTypesResponse> getFilteredTypes(Long userId, String name, Pageable pageable) {
        return expenseTypesRepository.findFiltered(userId, name, pageable).map(this::toResponse);
       // your existing toResponse() method
    }

    public ExpenseTypesResponse getById(Long id) {
        return expenseTypesRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new RuntimeException("Expense type not found"));
    }

    public void deleteType(Long id) {
        expenseTypesRepository.deleteById(id);
    }

    public ExpenseTypesResponse toResponse(ExpenseTypes type) {
        return ExpenseTypesResponse.builder()
                .id(type.getId())
                .name(type.getName())
                .createdAt(type.getCreatedAt())
                .updatedAt(type.getUpdatedAt())
                .createdBy(type.getCreatedBy() != null ? type.getCreatedBy().getFirstName() + " " + type.getCreatedBy().getLastName() : null)
                .updatedBy(type.getUpdatedBy() != null ? type.getCreatedBy().getFirstName() + " " + type.getCreatedBy().getLastName() : null)
                .build();
    }
}
