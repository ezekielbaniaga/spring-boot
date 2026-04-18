package ezekiel.baniaga.springboot.maven.backend.expense.api;

import ezekiel.baniaga.springboot.maven.backend.expense.dto.*;
import ezekiel.baniaga.springboot.maven.backend.expense.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/expense")
public class ExpenseController {

    @Autowired
    ExpenseService service;

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
}
