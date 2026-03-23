package ezekiel.baniaga.springboot.maven.backend.expense.repo;

import ezekiel.baniaga.springboot.maven.backend.expense.entity.Category;
import ezekiel.baniaga.springboot.maven.backend.expense.entity.Expense;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository("in-memory")
public class InMemoryExpenseRepository implements ExpenseRepository {

    @Override
    public List<Expense> findAll() {
        Expense e1 = new Expense();
//        e1.id = UUID.randomUUID();
//        e1.description = "2 kg. of Rice";
//        e1.amount = BigDecimal.valueOf(100.50);
//        e1.category = Category.FOOD;
//        e1.date = LocalDate.of(2025,3,22);

        return List.of(
                e1
        );
    }
}
