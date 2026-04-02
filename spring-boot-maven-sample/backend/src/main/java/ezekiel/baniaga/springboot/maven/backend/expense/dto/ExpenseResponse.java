package ezekiel.baniaga.springboot.maven.backend.expense.dto;

import ezekiel.baniaga.springboot.maven.backend.expense.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter @AllArgsConstructor
public class ExpenseResponse {
    private UUID id;
    private String description;
    private BigDecimal amount;
    private Category category;
    private LocalDate date;
}
