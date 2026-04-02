package ezekiel.baniaga.springboot.maven.backend.expense.repo;

import ezekiel.baniaga.springboot.maven.backend.expense.entity.Expense;

import java.util.List;

public interface ExpenseRepository {

    List<Expense> findAll();

    Expense add(Expense expense);

}
