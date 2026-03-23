package ezekiel.baniaga.springboot.maven.backend.expense.dto;

import ezekiel.baniaga.springboot.maven.backend.expense.entity.Category;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreateExpenseRequest {
    public String description;
    public BigDecimal amount;
    public Category category;
    public LocalDate date;
}
