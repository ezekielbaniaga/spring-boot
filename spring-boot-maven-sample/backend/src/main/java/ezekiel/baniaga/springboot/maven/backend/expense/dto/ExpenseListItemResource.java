package ezekiel.baniaga.springboot.maven.backend.expense.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class ExpenseListItemResource {
    public UUID id;
    public String description;
    public BigDecimal amount;
}
