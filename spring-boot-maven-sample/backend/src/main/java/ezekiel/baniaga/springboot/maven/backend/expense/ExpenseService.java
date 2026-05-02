package ezekiel.baniaga.springboot.maven.backend.expense;

import com.fasterxml.uuid.Generators;
import ezekiel.baniaga.springboot.maven.backend.common.ResourceNotFoundException;
import ezekiel.baniaga.springboot.maven.backend.expense.dto.*;
import ezekiel.baniaga.springboot.maven.backend.expense.entity.Category;
import ezekiel.baniaga.springboot.maven.backend.expense.entity.Expense;
import ezekiel.baniaga.springboot.maven.backend.expense.mapper.ExpenseMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ExpenseService {

    private final ExpenseRepository repository;
    private final ExpenseMapper expenseMapper;

    public ExpenseService(ExpenseRepository repository, ExpenseMapper expenseMapper) {
        this.repository = repository;
        this.expenseMapper = expenseMapper;
    }

    public ExpenseResponse getExpenseByUniqueId(UUID uniqueId) {
        //TODO: Add include archived=true, if yes can retrieve
        // record from archived
        Expense expense = findExpenseOrThrow(uniqueId);
        return expenseMapper.toResponse(expense);
    }

    public void deleteExpense(UUID uniqueId) {
        Expense expense = findExpenseOrThrow(uniqueId);
        // repository.delete(expense); --> hard delete
        expense.setArchived(true);
        expense.setArchivedAt(LocalDateTime.now());
        repository.save(expense);
    }

    public ExpenseListResponse getAllExpenses() {
        //TODO: Don't include archived records
        List<ExpenseListItemResponse> expensesResponse =
            repository.findAll().stream().map(expenseMapper::toListItem).toList();

        return new ExpenseListResponse(
            expensesResponse, expensesResponse.size());
    }

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

    public ExpenseResponse addExpense(CreateExpenseRequest request) {
        Expense expense = expenseMapper.toEntity(request);

        // UUID Version 7
        UUID uuid = Generators.timeBasedEpochGenerator().generate();
        expense.setUniqueId(uuid);
        expense.setCreatedAt(LocalDateTime.now());
        return expenseMapper.toResponse(repository.save(expense));
    }

    public AllCategoriesResponse getAllCategories() {
        return new AllCategoriesResponse(
            Category.values());
    }

    //TODO: Create similar but does not return archive so that in
    // findByUniqueId we can add option to include archived=true.
    private Expense findExpenseOrThrow(UUID uniqueId) {
        return repository.findByUniqueId(uniqueId)
                .orElseThrow(()->new ResourceNotFoundException("EXPENSE_NOT_FOUND"));
    }


    //TODO: Create schedule cron job for deleting
    // archived expense based on retention policy
}
