package com.expense.expenseTypes;

import com.expense.expenseTypes.dtos.ExpenseTypesRequest;
import com.expense.expenseTypes.dtos.ExpenseTypesResponse;
import com.expense.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/expense-types")
@RequiredArgsConstructor
public class ExpenseTypesController {

    private final ExpenseTypesService expenseTypesService;

    private User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @PostMapping
    public ResponseEntity<ExpenseTypesResponse> create(@RequestBody ExpenseTypesRequest request) {
        User user = getCurrentUser();
        return ResponseEntity.ok(expenseTypesService.createType(request, user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseTypesResponse> update(@PathVariable Long id, @RequestBody ExpenseTypesRequest request) {
        User user = getCurrentUser();
        return ResponseEntity.ok(expenseTypesService.updateType(id, request, user));
    }

    @GetMapping
    public ResponseEntity<List<ExpenseTypesResponse>> getAll() {
        return ResponseEntity.ok(expenseTypesService.getAllTypes());
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<ExpenseTypesResponse>> filterTypes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Long user_id,
            @RequestParam(required = false) String name
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));

        return ResponseEntity.ok(expenseTypesService.getFilteredTypes(user_id, name, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseTypesResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(expenseTypesService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        expenseTypesService.deleteType(id);
        return ResponseEntity.noContent().build();
    }

}
