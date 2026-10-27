package com.projects.ruleEngine;

import com.projects.ruleEngine.enums.ExpenseType;
import com.projects.ruleEngine.enums.SellerType;
import com.projects.ruleEngine.models.Expense;
import com.projects.ruleEngine.models.Violation;
import com.projects.ruleEngine.rules.impl.DisallowedRule;
import com.projects.ruleEngine.rules.impl.TotalMaxAmountRule;
import com.projects.ruleEngine.rules.ExpenseRule;

import java.util.ArrayList;
import java.util.List;

public class RuleEvaluator {

    public static void main(String[] args) {
        List<Expense> expenses = List.of(
                new Expense("1", "Item1", ExpenseType.FOOD, 200,
                        SellerType.RESTAURANT, "ABC restaurant"),
                new Expense("2", "Item2", ExpenseType.ENTERTAINMENT, 50,
                        SellerType.MARKET, "Arcade"),
                new Expense("3", "Item3", ExpenseType.FOOD, 30,
                        SellerType.MARKET, "Local market")
        );

        List<Integer> expectedViolationCounts = List.of(2, 1, 0);
        for (int i = 0; i < expenses.size(); i++) {
            Expense expense = expenses.get(i);
            List<Violation> violations = evaluateExpense(expense);
            if (violations.size() != expectedViolationCounts.get(i)) {
                throw new AssertionError("Unexpected violations for expense " + expense.getExpenseId());
            }

            System.out.println("Expense " + expense.getExpenseId() + ":");
            if (violations.isEmpty()) {
                System.out.println("  No violations");
            } else {
                for (Violation violation : violations) {
                    System.out.println("  " + violation.getMessage());
                }
            }
        }
    }

    private static List<Violation> evaluateExpense(Expense expense) {
        List<ExpenseRule> rules = new ArrayList<>();
        rules.add(new TotalMaxAmountRule(175));
        if (expense.getSellerType() == SellerType.RESTAURANT) {
            rules.add(new TotalMaxAmountRule(45));
        }
        if (expense.getExpenseType() == ExpenseType.ENTERTAINMENT) {
            rules.add(new DisallowedRule(ExpenseType.ENTERTAINMENT));
        }

        List<Violation> violations = new ArrayList<>();
        for (ExpenseRule rule : rules) {
            rule.check(expense).ifPresent(violations::add);
        }
        return violations;
    }
}
