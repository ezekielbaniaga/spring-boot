package ezekiel.baniaga.springboot.maven.backend.expense;

import ezekiel.baniaga.springboot.maven.backend.expense.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/expense")
public class ExpenseController {

    private final ExpenseService service;

    public ExpenseController(ExpenseService service) {
        this.service = service;
    }

    @GetMapping(version = "1.0")
    public ExpenseListResponse getExpenses() {
        return service.getAllExpenses();
    }

    @GetMapping(version = "1.1")
    public ExpenseListResponseV1_1 getExpenseV1_1() {
        return service.getAllExpensesV1_1();
    }

    @PostMapping(version = "1.0")
    public ExpenseResponse createExpense(@Valid @RequestBody CreateExpenseRequest request) {
        return service.addExpense(request);
    }

    @GetMapping(value = "/categories", version = "1.0")
    public AllCategoriesResponse getSupportedCategories() {
        return service.getAllCategories();
    }

    @GetMapping(value = "/{uniqueId}")
    public ExpenseResponse getByUniqueId(@PathVariable UUID uniqueId) {
        return service.getExpenseByUniqueId(uniqueId);
    }

    @DeleteMapping(value = "/{uniqueId}")
    public ResponseEntity<Void> deleteByUniqueId(@PathVariable UUID uniqueId) {
        service.deleteExpense(uniqueId);
        return ResponseEntity.noContent().build();
    }
}
