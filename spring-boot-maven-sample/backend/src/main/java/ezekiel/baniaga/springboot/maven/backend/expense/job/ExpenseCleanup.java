package ezekiel.baniaga.springboot.maven.backend.expense.job;

import ezekiel.baniaga.springboot.maven.backend.expense.ExpenseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ExpenseCleanup {

    // 30 days retention policy for archived expenses
    private static final int RETENTION_MAX_DAYS = 30;
    private static final int MAX_ROWS_DELETE = 1000;

    private final ExpenseService expenseService;

    public ExpenseCleanup(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    // For every minute at 2AM, do nightly cleanup of MAX_ROWS_DELETE
    @Scheduled(cron = "0 * 2 * * *")
    public void cleanup() {
        log.info("Cleaning Archived Expenses...");
        int affectedRows = expenseService.cleanupArchived(RETENTION_MAX_DAYS, MAX_ROWS_DELETE);
        log.info("Affected Rows: {}",affectedRows);
    }

}
