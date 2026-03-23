package ezekiel.baniaga.springboot.maven.backend.expense.service;

import ezekiel.baniaga.springboot.maven.backend.expense.dto.CreateExpenseRequest;
import ezekiel.baniaga.springboot.maven.backend.expense.dto.ExpenseListItemResource;
import ezekiel.baniaga.springboot.maven.backend.expense.dto.ExpenseListResource;
import ezekiel.baniaga.springboot.maven.backend.expense.dto.ExpenseResource;
import ezekiel.baniaga.springboot.maven.backend.expense.entity.Category;
import ezekiel.baniaga.springboot.maven.backend.expense.entity.Expense;
import ezekiel.baniaga.springboot.maven.backend.expense.repo.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    @Qualifier("in-memory")
    ExpenseRepository repository;

    @Override
    public ExpenseListResource getAllExpenses() {
        ExpenseListResource el_res = new ExpenseListResource();
        el_res.expenses = repository.findAll().stream().map(expense -> {

            ExpenseListItemResource item = new ExpenseListItemResource();
//            item.id = expense.id;
//            item.amount = expense.amount;
//            item.description = expense.description;
            return item;

        }).toList();

        return el_res;
    }

    implement itong mga to at itest ang validation errors
    @Override
    public ExpenseResource addExpense(CreateExpenseRequest request) {
        Expense expense = new Expense();
//        expense.id = UUID.randomUUID();
//        expense.amount = request.amount;
//        expense.category = request.category;
//        expense.description = request.description;
//        expense.date = request.date;
//        expense.createdAt = LocalDateTime.now();
        return null;
    }
}
