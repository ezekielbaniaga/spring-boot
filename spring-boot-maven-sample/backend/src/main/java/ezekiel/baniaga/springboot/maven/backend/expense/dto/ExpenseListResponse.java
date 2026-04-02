package ezekiel.baniaga.springboot.maven.backend.expense.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter @AllArgsConstructor
public class ExpenseListResponse {
    private List<ExpenseListItemResponse> expenses;
    private int total;
}
