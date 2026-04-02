package ezekiel.baniaga.springboot.maven.backend.expense.repo;

import ezekiel.baniaga.springboot.maven.backend.expense.entity.Category;
import ezekiel.baniaga.springboot.maven.backend.expense.entity.Expense;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository("in-memory")
public class InMemoryExpenseRepository implements ExpenseRepository {

    private ArrayList<Expense> expenses = new ArrayList<>();

    @Override
    public List<Expense> findAll() {
        return expenses;
    }

    @Override
    public Expense add(Expense expense) {
        expenses.add(expense);
        expense.setId(UUID.randomUUID());
        expense.setCreatedAt(LocalDateTime.now());
        return expense;
    }
}
