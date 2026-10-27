package com.projects.ruleEngine.models;


import com.projects.ruleEngine.enums.ExpenseType;
import com.projects.ruleEngine.enums.SellerType;

/*
{
	expenseid: "1"
	itemId: "Item1"
	expensetype: "Food"
	amountInUsd : "250"
	sellerType : "restaurant"
	SellerName "ABC restaurant"

}
 */
public class Expense {
    private String expenseId;
    private String itemId;
    private ExpenseType expenseType;
    private Integer amountInUsd;
    private SellerType sellerType;
    private String sellerName;

    public Expense(String expenseId, String itemId, ExpenseType expenseType, Integer amountInUsd, SellerType sellerType, String sellerName) {
        this.expenseId = expenseId;
        this.itemId = itemId;
        this.expenseType = expenseType;
        this.amountInUsd = amountInUsd;
        this.sellerType = sellerType;
        this.sellerName = sellerName;
    }


    public String getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(String expenseId) {
        this.expenseId = expenseId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public ExpenseType getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(ExpenseType expenseType) {
        this.expenseType = expenseType;
    }

    public Integer getAmountInUsd() {
        return amountInUsd;
    }

    public void setAmountInUsd(Integer amountInUsd) {
        this.amountInUsd = amountInUsd;
    }

    public SellerType getSellerType() {
        return sellerType;
    }

    public void setSellerType(SellerType sellerType) {
        this.sellerType = sellerType;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }
}
