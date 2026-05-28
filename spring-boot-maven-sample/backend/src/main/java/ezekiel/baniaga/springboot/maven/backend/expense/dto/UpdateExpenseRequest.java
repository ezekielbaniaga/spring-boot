package ezekiel.baniaga.springboot.maven.backend.expense.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class UpdateExpenseRequest {

    @PositiveOrZero
    private Long version;

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
