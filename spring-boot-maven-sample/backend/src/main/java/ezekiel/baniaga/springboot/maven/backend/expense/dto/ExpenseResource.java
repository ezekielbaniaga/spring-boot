package ezekiel.baniaga.springboot.maven.backend.expense.dto;

import ezekiel.baniaga.springboot.maven.backend.expense.entity.Category;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class ExpenseResource {
    public UUID id;
    public String description;
    public BigDecimal amount;
    public Category category;
    public LocalDate date;
}
