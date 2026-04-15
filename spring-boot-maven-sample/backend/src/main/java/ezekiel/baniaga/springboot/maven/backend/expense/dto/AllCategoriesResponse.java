package ezekiel.baniaga.springboot.maven.backend.expense.dto;

import ezekiel.baniaga.springboot.maven.backend.expense.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class AllCategoriesResponse {
    private Category[] supported_categories;
}
