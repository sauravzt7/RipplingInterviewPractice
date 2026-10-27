package com.projects.ruleEngine.rules.impl;

import com.projects.ruleEngine.enums.ExpenseType;
import com.projects.ruleEngine.models.Expense;
import com.projects.ruleEngine.models.Violation;
import com.projects.ruleEngine.rules.ExpenseRule;

import java.util.Optional;

public class DisallowedRule implements ExpenseRule {

    private ExpenseType expenseType;

    public DisallowedRule(ExpenseType expenseType) {
        this.expenseType = expenseType;
    }

    @Override
    public Optional<Violation> check(Expense expense) {
        if(expense.getExpenseType() == expenseType) return Optional.of(Violation.of("Expense"+":id"+ expense.getExpenseType() + ":is not allowed"));
        return Optional.empty();
    }

}
