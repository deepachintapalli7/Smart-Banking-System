
package com.novara;

import com.novara.model.Account;
import com.novara.service.AccountService;
import com.novara.service.BankingService;

public class NovaraTest {

    public static void main(String[] args) {

        AccountService accountService = new AccountService();
        BankingService banking = new BankingService(accountService);

        Account alice = accountService.createAccount("C001", "SAVINGS");
        Account bob = accountService.createAccount("C002", "SAVINGS");

        banking.deposit(alice.getAccountNumber(), 100000, "TXN001");

        banking.transfer(
            alice.getAccountNumber(),
            bob.getAccountNumber(),
            30000,
            "TXN002"
        );

        System.out.println("Alice balance: ₹" +
            banking.getBalance(alice.getAccountNumber()) / 100.0);

        System.out.println("Bob balance: ₹" +
            banking.getBalance(bob.getAccountNumber()) / 100.0);

        System.out.println("Alice transactions: " +
            banking.getTransactionHistory(alice.getAccountNumber()).size());

        System.out.println("NOVARA TEST COMPLETED!");

System.out.println("\n--- NOVARA SECURITY TESTS ---");

// Test 1: Insufficient balance
try {
    banking.withdraw(alice.getAccountNumber(), 80000, "TXN003");
    System.out.println("FAILED: Insufficient balance accepted!");
} catch (IllegalArgumentException e) {
    System.out.println("PASSED: Insufficient balance rejected!");
}

// Test 2: Duplicate transaction
try {
    banking.deposit(alice.getAccountNumber(), 10000, "TXN001");
    System.out.println("FAILED: Duplicate transaction accepted!");
} catch (IllegalArgumentException e) {
    System.out.println("PASSED: Duplicate transaction rejected!");
}

// Test 3: Invalid amount
try {
    banking.deposit(alice.getAccountNumber(), 0, "TXN004");
    System.out.println("FAILED: Invalid amount accepted!");
} catch (IllegalArgumentException e) {
    System.out.println("PASSED: Invalid amount rejected!");
}
System.out.println("\n--- TRUSTGUARD TEST ---");

com.novara.service.TrustGuardService guard =
    new com.novara.service.TrustGuardService();

if (guard.isSuspicious(50000000L)) {
    System.out.println("PASSED: Large transaction flagged!");
} else {
    System.out.println("FAILED: Large transaction not flagged!");
}
    }
}
