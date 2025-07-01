package com.expense.income;

import com.expense.income.dto.IncomeRequest;
import com.expense.income.dto.IncomeResponse;
import com.expense.income.IncomeService;
import com.expense.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/income")
@RequiredArgsConstructor
public class IncomeController {

    private final IncomeService incomeService;

    private User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @PostMapping
    public ResponseEntity<IncomeResponse> addIncome(@RequestBody IncomeRequest request) {
        return ResponseEntity.ok(incomeService.createIncome(getCurrentUser(), request));
    }

    @GetMapping
    public ResponseEntity<Page<IncomeResponse>> getIncomes(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(incomeService.getAllUserIncomes(getCurrentUser(), pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<IncomeResponse> getIncomeById(@PathVariable Long id) {
        return ResponseEntity.ok(incomeService.getIncomeById(getCurrentUser(), id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IncomeResponse> updateIncome(@PathVariable Long id, @RequestBody IncomeRequest request) {
        return ResponseEntity.ok(incomeService.updateIncome(getCurrentUser(), id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncome(@PathVariable Long id) {
        incomeService.deleteIncome(getCurrentUser(), id);
        return ResponseEntity.noContent().build();
    }
}
