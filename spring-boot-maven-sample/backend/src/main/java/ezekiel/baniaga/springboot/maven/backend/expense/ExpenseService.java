package ezekiel.baniaga.springboot.maven.backend.expense;

import com.fasterxml.uuid.Generators;
import ezekiel.baniaga.springboot.maven.backend.common.BusinessRuleException;
import ezekiel.baniaga.springboot.maven.backend.common.ResourceNotFoundException;
import ezekiel.baniaga.springboot.maven.backend.expense.dto.*;
import ezekiel.baniaga.springboot.maven.backend.expense.entity.Category;
import ezekiel.baniaga.springboot.maven.backend.expense.entity.Expense;
import ezekiel.baniaga.springboot.maven.backend.expense.mapper.ExpenseMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ConcurrentModificationException;
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
        Expense expense = findExpenseOrThrow(uniqueId);
        return expenseMapper.toResponse(expense);
    }

    @Transactional
    public ExpenseResponse updateExpense(UUID uniqueId, UpdateExpenseRequest request) {
        Expense expense = findExpenseOrThrow(uniqueId);

        if (!expense.getVersion().equals(request.getVersion())) {
            throw new BusinessRuleException("CONCURRENT_MODIFICATION","Expense was modified by another user");
        }

        if (Boolean.TRUE.equals(expense.getArchived())) {
            throw new BusinessRuleException("MODIFY_ARCHIVED","Cannot modify archived expense");
        }

        expense = expenseMapper.toEntity(request, expense);
        expense.setLastModified(LocalDateTime.now());

        // Using saveAndFlush to get latest version field incremented by Hibernate
        return expenseMapper.toResponse(repository.saveAndFlush(expense));
    }

    public void deleteExpense(UUID uniqueId) {
        Expense expense = findExpenseOrThrow(uniqueId);
        // repository.delete(expense); --> hard delete
        expense.setArchived(true);
        expense.setArchivedAt(LocalDateTime.now());
        repository.save(expense);
    }

    public ExpenseListResponse getAllExpenses() {
        List<ExpenseListItemResponse> expensesResponse = findAllExpensesAndMapToListItem();

        return new ExpenseListResponse(
            expensesResponse, expensesResponse.size());
    }

    public ExpenseListResponseV1_1 getAllExpensesV1_1() {
        List<ExpenseListItemResponse> expensesResponse = findAllExpensesAndMapToListItem();

        int count = expensesResponse.size();
        BigDecimal total_amount = expensesResponse.stream()
                .map(ExpenseListItemResponse::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new ExpenseListResponseV1_1(
                expensesResponse, count, total_amount);
    }

    public ArchivedExpenseListResponse getArchivedExpenses() {
        List<ArchivedExpenseListItemResponse> expensesResponse = repository.findAllByArchivedIsTrue()
                .stream().map(expenseMapper::toArchivedListItem).toList();

        return new ArchivedExpenseListResponse(expensesResponse, expensesResponse.size());
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

    private List<ExpenseListItemResponse> findAllExpensesAndMapToListItem() {
        return repository.findAllByArchivedIsNullOrArchivedIsFalse()
            .stream().map(expenseMapper::toListItem).toList();
    }

    private Expense findExpenseOrThrow(UUID uniqueId) {
        return repository.findByUniqueId(uniqueId)
                .orElseThrow(()->new ResourceNotFoundException("EXPENSE_NOT_FOUND"));
    }

    @Transactional
    public int cleanupArchived(int retentionMaxDays, int limit) {
        LocalDateTime cutoff = LocalDateTime.now().minusDays(retentionMaxDays);
        return repository.deleteOldExpense(cutoff, limit);
    }

}
