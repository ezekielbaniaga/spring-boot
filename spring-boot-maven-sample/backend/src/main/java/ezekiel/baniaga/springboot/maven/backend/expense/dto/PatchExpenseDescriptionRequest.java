package ezekiel.baniaga.springboot.maven.backend.expense.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PatchExpenseDescriptionRequest {

    @PositiveOrZero
    private Long version;

    @NotBlank
    @Size(max=250)
    private String description;

}
