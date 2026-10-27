package com.projects.ruleEngine.services;

import com.projects.ruleEngine.enums.ExpenseType;
import com.projects.ruleEngine.models.Expense;
import com.projects.ruleEngine.models.Violation;
import com.projects.ruleEngine.registry.RuleRegistry;
import com.projects.ruleEngine.rules.ExpenseRule;
import com.projects.ruleEngine.rules.TripRule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class RuleEngine {




    public void evaluate(List<Expense> expenses, Map<ExpenseType, List<ExpenseRule>> expenseRulesRegistry,
                         List<TripRule> tripRulesRegistry, List<ExpenseRule> allExpenseRules){

        List<Violation> violations = new ArrayList<>();

        for(Expense e: expenses){
            List<ExpenseRule> rules = RuleRegistry.getAllExpenseTypeRuleRegistry().get(e.getExpenseType());

            for(ExpenseRule rule : rules){
                Optional<Violation> violation = rule.check(e);
                violation.ifPresent(violations::add);
            }

            for(ExpenseRule rule : allExpenseRules){
                Optional<Violation> violation = rule.check(e);
                violation.ifPresent(violations::add);
            }

            for(ExpenseRule rule : RuleRegistry.getAllSellerTypeRuleRegistry().get(e.getSellerType())){
                Optional<Violation> violation = rule.check(e);
                violation.ifPresent(violations::add);
            }

            for(TripRule rule : tripRulesRegistry){
                Optional<Violation> violation = rule.check(expenses);
                violation.ifPresent(violations::add);
            }
        }
    }


}
