package ezekiel.baniaga.springboot.maven.backend.expense.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter @AllArgsConstructor
public class ArchivedExpenseListResponse {
    private List<ArchivedExpenseListItemResponse> expenses;
    private int count;
}
