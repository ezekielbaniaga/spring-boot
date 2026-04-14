package ezekiel.baniaga.springboot.maven.backend.expense.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Setter @NoArgsConstructor
public class Expense {
    private UUID id;
    private String description;
    private BigDecimal amount;
    private Category category;
    private LocalDate date;
    private LocalDateTime createdAt;
}
