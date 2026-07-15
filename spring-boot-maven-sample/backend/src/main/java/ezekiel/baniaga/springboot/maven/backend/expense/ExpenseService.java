package ezekiel.baniaga.springboot.maven.backend.expense;

import ezekiel.baniaga.springboot.maven.backend.common.BusinessRuleException;
import ezekiel.baniaga.springboot.maven.backend.common.ResourceNotFoundException;
import ezekiel.baniaga.springboot.maven.backend.config.DefaultUUIDGenerator;
import ezekiel.baniaga.springboot.maven.backend.expense.dto.*;
import ezekiel.baniaga.springboot.maven.backend.expense.entity.Category;
import ezekiel.baniaga.springboot.maven.backend.expense.entity.Expense;
import ezekiel.baniaga.springboot.maven.backend.expense.mapper.ExpenseMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository repository;
    private final ExpenseMapper expenseMapper;
    private final DefaultUUIDGenerator uuidGenerator;

    public ExpenseResponse getExpenseByUniqueId(UUID uniqueId) {
        Expense expense = findExpenseOrThrow(uniqueId);
        return expenseMapper.toResponse(expense);
    }

    @Transactional
    public ExpenseResponse updateExpense(UUID uniqueId, UpdateExpenseRequest request) {
        Expense expense = findExpenseOrThrow(uniqueId);

        checkVersion(expense, request.version());
        checkIfArchived(expense);

        expense = expenseMapper.toEntity(request, expense);
        expense.setLastModified(LocalDateTime.now());

        // Using saveAndFlush to get latest version field incremented by Hibernate
        return expenseMapper.toResponse(repository.saveAndFlush(expense));
    }

    @Transactional
    public ExpenseResponse patchExpenseDescription(UUID uniqueId, PatchExpenseDescriptionRequest request) {
        Expense expense = findExpenseOrThrow(uniqueId);

        checkVersion(expense, request.version());
        checkIfArchived(expense);

        expense.setDescription(request.description());
        expense.setLastModified(LocalDateTime.now());

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
                .map(ExpenseListItemResponse::amount)
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

        UUID uuid = uuidGenerator.generate();
        expense.setUniqueId(uuid);
        expense.setCreatedAt(LocalDateTime.now());
        return expenseMapper.toResponse(repository.save(expense));
    }

    public AllCategoriesResponse getAllCategories() {
        return new AllCategoriesResponse(
            Category.values());
    }

    private void checkIfArchived(Expense expense) {
        if (Boolean.TRUE.equals(expense.getArchived())) {
            throw new BusinessRuleException("MODIFY_ARCHIVED","Cannot modify archived expense");
        }
    }

    private void checkVersion(Expense expense, Long version) {
        if (!expense.getVersion().equals(version)) {
            throw new BusinessRuleException("CONCURRENT_MODIFICATION","Expense was modified by another user");
        }
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
