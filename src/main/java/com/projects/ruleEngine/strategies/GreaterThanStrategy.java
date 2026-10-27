package com.projects.ruleEngine.strategies;

public class GreaterThanStrategy implements ComparisonStrategy{

    private Integer amount;

    @Override
    public boolean compare(int val) {
        return val > amount;
    }
}
