package ezekiel.baniaga.springboot.maven.backend.expense.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateExpenseRequest (

    @PositiveOrZero
    Long version,

    @NotBlank
    @Size(max=250)
    String description,

    @Positive
    BigDecimal amount,

    @NotBlank
    String category,

    @PastOrPresent
    LocalDate date

){}
