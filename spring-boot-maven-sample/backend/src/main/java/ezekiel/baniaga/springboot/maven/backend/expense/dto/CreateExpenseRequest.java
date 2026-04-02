package ezekiel.baniaga.springboot.maven.backend.expense.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CreateExpenseRequest {
    private String description;
    private BigDecimal amount;
    private String category;
    private LocalDate date;
}
