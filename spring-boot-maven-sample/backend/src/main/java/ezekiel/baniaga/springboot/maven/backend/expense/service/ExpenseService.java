package ezekiel.baniaga.springboot.maven.backend.expense.service;

import ezekiel.baniaga.springboot.maven.backend.expense.dto.CreateExpenseRequest;
import ezekiel.baniaga.springboot.maven.backend.expense.dto.ExpenseListResponse;
import ezekiel.baniaga.springboot.maven.backend.expense.dto.ExpenseResponse;

public interface ExpenseService {

    ExpenseListResponse getAllExpenses();

    ExpenseResponse addExpense(CreateExpenseRequest request);


}
