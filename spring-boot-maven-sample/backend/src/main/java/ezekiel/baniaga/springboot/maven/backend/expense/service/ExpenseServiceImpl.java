package ezekiel.baniaga.springboot.maven.backend.expense.service;

import ezekiel.baniaga.springboot.maven.backend.expense.dto.*;
import ezekiel.baniaga.springboot.maven.backend.expense.entity.Category;
import ezekiel.baniaga.springboot.maven.backend.expense.entity.Expense;
import ezekiel.baniaga.springboot.maven.backend.expense.mapper.ExpenseMapper;
import ezekiel.baniaga.springboot.maven.backend.expense.repo.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    public ExpenseListResponseV1_1 getAllExpensesV1_1() {
        List<ExpenseListItemResponse> expensesResponse =
                repository.findAll().stream().map(expenseMapper::toListItem).toList();

        int count = expensesResponse.size();
        BigDecimal total_amount = expensesResponse.stream()
                .map(ExpenseListItemResponse::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new ExpenseListResponseV1_1(
                expensesResponse, count, total_amount);
    }

    @Override
    public ExpenseResponse addExpense(CreateExpenseRequest request) {
        Expense expense = expenseMapper.toEntity(request);
        return expenseMapper.toResponse(repository.add(expense));
    }

    @Override
    public AllCategoriesResponse getAllCategories() {
        return new AllCategoriesResponse(
            Category.values());
    }


}
