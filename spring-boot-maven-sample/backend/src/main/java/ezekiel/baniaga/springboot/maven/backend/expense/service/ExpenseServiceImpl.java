package ezekiel.baniaga.springboot.maven.backend.expense.service;

import ezekiel.baniaga.springboot.maven.backend.expense.dto.CreateExpenseRequest;
import ezekiel.baniaga.springboot.maven.backend.expense.dto.ExpenseListItemResponse;
import ezekiel.baniaga.springboot.maven.backend.expense.dto.ExpenseListResponse;
import ezekiel.baniaga.springboot.maven.backend.expense.dto.ExpenseResponse;
import ezekiel.baniaga.springboot.maven.backend.expense.entity.Expense;
import ezekiel.baniaga.springboot.maven.backend.expense.mapper.ExpenseMapper;
import ezekiel.baniaga.springboot.maven.backend.expense.repo.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    @Qualifier("in-memory")
    ExpenseRepository repository;

    @Autowired
    ExpenseMapper expenseMapper;

    @Override
    public ExpenseListResponse getAllExpenses() {
        List<ExpenseListItemResponse> expensesResponse =
            repository.findAll().stream().map(expenseMapper::toListItem).toList();

        return new ExpenseListResponse(
            expensesResponse, expensesResponse.size());
    }

    @Override
    public ExpenseResponse addExpense(CreateExpenseRequest request) {
        Expense expense = expenseMapper.toEntity(request);
        return expenseMapper.toResponse(repository.add(expense));
    }
}
