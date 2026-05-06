package ezekiel.baniaga.springboot.maven.backend.expense.dto;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class ArchivedExpenseListItemResponse extends ExpenseListItemResponse {
    private final LocalDateTime archivedAt;

    public ArchivedExpenseListItemResponse(UUID id, String description, BigDecimal amount, LocalDateTime archivedAt) {
        super(id, description, amount);
        this.archivedAt = archivedAt;
    }
}
