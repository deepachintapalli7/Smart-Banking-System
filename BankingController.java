package com.novara.service;

public class BankingController {

    public static final AccountService accountService =
            new AccountService();

    public static final BankingService bankingService =
            new BankingService(accountService);

    public static final BudgetPlannerService budgetService =
            new BudgetPlannerService();
}