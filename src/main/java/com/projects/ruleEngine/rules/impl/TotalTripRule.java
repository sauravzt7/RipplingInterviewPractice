package com.projects.ruleEngine.rules.impl;

import com.projects.ruleEngine.models.Expense;
import com.projects.ruleEngine.models.Violation;
import com.projects.ruleEngine.rules.TripRule;

import java.util.List;
import java.util.Optional;

public class TotalTripRule implements TripRule {

    private double maxTotal;

    public TotalTripRule(double maxTotal){
        this.maxTotal = maxTotal;
    }

    @Override
    public Optional<Violation> check(List<Expense> expenses) {
        // TODO check if all expenses under the same trip

        if(expenses.stream().mapToDouble(e -> e.getAmountInUsd()).sum() > maxTotal){
            return Optional.of(new Violation("Total trip amount exceeds max limit"));
        }
        return Optional.empty();
    }
}
