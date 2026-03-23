package ezekiel.baniaga.springboot.maven.backend.expense.service;

import ezekiel.baniaga.springboot.maven.backend.expense.dto.CreateExpenseRequest;
import ezekiel.baniaga.springboot.maven.backend.expense.dto.ExpenseListResource;
import ezekiel.baniaga.springboot.maven.backend.expense.dto.ExpenseResource;
import ezekiel.baniaga.springboot.maven.backend.expense.entity.Category;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface ExpenseService {

    ExpenseListResource getAllExpenses();

    ExpenseResource addExpense(CreateExpenseRequest request);


}
