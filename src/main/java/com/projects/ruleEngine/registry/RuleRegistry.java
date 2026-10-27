package com.projects.ruleEngine.registry;

import com.projects.ruleEngine.enums.ExpenseType;
import com.projects.ruleEngine.enums.SellerType;
import com.projects.ruleEngine.rules.ExpenseRule;
import com.projects.ruleEngine.rules.TripRule;
import com.projects.ruleEngine.rules.impl.DisallowedRule;
import com.projects.ruleEngine.rules.impl.TotalMaxAmountRule;
import com.projects.ruleEngine.rules.impl.TotalTripRule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RuleRegistry {

    public static Map<ExpenseType, List<ExpenseRule>> getAllExpenseTypeRuleRegistry(){
        Map<ExpenseType, List<ExpenseRule>> registry = new HashMap<>();
        registry.put(ExpenseType.ENTERTAINMENT, List.of(new DisallowedRule(ExpenseType.ENTERTAINMENT)));
        registry.put(ExpenseType.FOOD, List.of(new TotalMaxAmountRule(200)));

        return registry;
    };

    public static Map<SellerType, List<ExpenseRule>> getAllSellerTypeRuleRegistry(){
        Map<SellerType, List<ExpenseRule>> registry = new HashMap<>();
        registry.put(SellerType.RESTAURANT, List.of(new TotalMaxAmountRule(45)));

        return registry;
    }

    public static List<TripRule> getAllExpenseRuleRegistry(){
        return List.of(new TotalTripRule(250));
    }

}
