package ezekiel.baniaga.springboot.maven.backend.expense.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CreateExpenseRequest {

    @NotBlank
    @Size(max=250)
    private String description;

    @Positive
    private BigDecimal amount;

    @NotBlank
    private String category;

    @PastOrPresent
    private LocalDate date;
}
