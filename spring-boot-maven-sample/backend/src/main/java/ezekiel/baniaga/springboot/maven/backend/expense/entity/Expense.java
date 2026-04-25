package ezekiel.baniaga.springboot.maven.backend.expense.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "expense")
@Getter @Setter @NoArgsConstructor
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID uniqueId;
    private String description;
    private BigDecimal amount;
    private Category category;
    private LocalDate expenseDate;
    private LocalDateTime createdAt;
}
