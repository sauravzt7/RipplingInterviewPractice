package com.projects.ruleEngine.rules;

import com.projects.ruleEngine.models.Expense;
import com.projects.ruleEngine.models.Violation;

import java.util.List;
import java.util.Optional;

public interface ExpenseRule {
    Optional<Violation> check(Expense expense);
}
