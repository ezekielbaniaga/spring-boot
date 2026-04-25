package ezekiel.baniaga.springboot.maven.backend.expense.mapper;

import ezekiel.baniaga.springboot.maven.backend.common.BadRequestException;
import ezekiel.baniaga.springboot.maven.backend.common.BusinessRuleException;
import ezekiel.baniaga.springboot.maven.backend.expense.dto.CreateExpenseRequest;
import ezekiel.baniaga.springboot.maven.backend.expense.dto.ExpenseListItemResponse;
import ezekiel.baniaga.springboot.maven.backend.expense.dto.ExpenseResponse;
import ezekiel.baniaga.springboot.maven.backend.expense.entity.Category;
import ezekiel.baniaga.springboot.maven.backend.expense.entity.Expense;
import org.springframework.stereotype.Component;

@Component
public class ExpenseMapper {

    public Expense toEntity(CreateExpenseRequest request) {
        Expense expense = new Expense();
        expense.setAmount(request.getAmount());
        expense.setCategory(parseCategory(request.getCategory()));
        expense.setDescription(request.getDescription());
        expense.setExpenseDate(request.getDate());
        return expense;
    }

    public ExpenseResponse toResponse(Expense expense) {
        return new ExpenseResponse(
            expense.getUniqueId(),
            expense.getDescription(),
            expense.getAmount(),
            expense.getCategory(),
            expense.getExpenseDate());
    }

    public ExpenseListItemResponse toListItem(Expense expense) {
        return new ExpenseListItemResponse(
            expense.getUniqueId(),
            expense.getDescription(),
            expense.getAmount());
    }

    private static Category parseCategory(String value) {
        if (value == null) return null;

        try {
            return Category.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("INVALID_CATEGORY", "Invalid category: " + value);
        }

    }
}
