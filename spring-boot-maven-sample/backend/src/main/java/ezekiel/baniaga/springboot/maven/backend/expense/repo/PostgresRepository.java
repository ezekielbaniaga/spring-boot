package ezekiel.baniaga.springboot.maven.backend.expense.repo;

import ezekiel.baniaga.springboot.maven.backend.expense.entity.Expense;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("postgres")
public class PostgresRepository implements ExpenseRepository {
    @Override
    public List<Expense> findAll() {
        return List.of();
    }

    @Override
    public Expense add(Expense expense) {
        throw new UnsupportedOperationException();
    }
}
