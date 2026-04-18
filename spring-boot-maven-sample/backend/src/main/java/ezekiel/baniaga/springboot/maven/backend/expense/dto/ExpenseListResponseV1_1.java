package ezekiel.baniaga.springboot.maven.backend.expense.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter @AllArgsConstructor
public class ExpenseListResponseV1_1 {
    private List<ExpenseListItemResponse> expenses;
    private int count;
    private BigDecimal total_amount;
}
