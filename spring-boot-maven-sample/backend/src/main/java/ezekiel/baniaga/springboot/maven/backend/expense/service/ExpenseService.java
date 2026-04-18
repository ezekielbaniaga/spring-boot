package ezekiel.baniaga.springboot.maven.backend.expense.service;

import ezekiel.baniaga.springboot.maven.backend.expense.dto.*;

public interface ExpenseService {

    ExpenseListResponse getAllExpenses();

    ExpenseListResponseV1_1 getAllExpensesV1_1();

    ExpenseResponse addExpense(CreateExpenseRequest request);

    AllCategoriesResponse getAllCategories();

}
