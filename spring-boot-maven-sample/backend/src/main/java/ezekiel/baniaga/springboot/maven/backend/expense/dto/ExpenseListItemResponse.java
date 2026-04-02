package ezekiel.baniaga.springboot.maven.backend.expense.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter @AllArgsConstructor
public class ExpenseListItemResponse {
    private UUID id;
    private String description;
    private BigDecimal amount;
}
