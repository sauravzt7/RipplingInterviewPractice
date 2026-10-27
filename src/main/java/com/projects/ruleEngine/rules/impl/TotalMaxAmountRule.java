package com.projects.ruleEngine.rules.impl;

import com.projects.ruleEngine.models.Expense;
import com.projects.ruleEngine.models.Violation;
import com.projects.ruleEngine.rules.ExpenseRule;

import java.util.Optional;

public class TotalMaxAmountRule implements ExpenseRule {


    private double maxAmount;

    public TotalMaxAmountRule(double   maxAmount) {
        this.maxAmount = maxAmount;
    }


    @Override
    public Optional<Violation> check(Expense expense) {
        if(expense.getAmountInUsd() > maxAmount) return Optional.of(Violation.of("Total amount exceeds max amount" + ":id" + expense.getExpenseId() + ":maxAmount" + maxAmount));
        return Optional.empty();
    }
}
