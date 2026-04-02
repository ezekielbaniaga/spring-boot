package ezekiel.baniaga.springboot.maven.backend.expense.api;

import ezekiel.baniaga.springboot.maven.backend.expense.dto.CreateExpenseRequest;
import ezekiel.baniaga.springboot.maven.backend.expense.dto.ExpenseListResponse;
import ezekiel.baniaga.springboot.maven.backend.expense.dto.ExpenseResponse;
import ezekiel.baniaga.springboot.maven.backend.expense.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.path}/expense")
public class ExpenseController {

    @Autowired
    ExpenseService service;

    @GetMapping
    public ExpenseListResponse getExpenses() {
        return service.getAllExpenses();
    }

    @PostMapping
    public ExpenseResponse createExpense(@RequestBody CreateExpenseRequest request) {
        return service.addExpense(request);
    }
}
