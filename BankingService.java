
package com.novara.service;

import com.novara.model.Account;
import com.novara.model.Transaction;
import com.novara.util.ValidationUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BankingService {

    private final AccountService accountService;
    private final TrustGuardService trustGuard;

    private final List<Transaction> transactions = new ArrayList<>();

    public BankingService(AccountService accountService) {
        this.accountService = accountService;
        this.trustGuard = new TrustGuardService();
    }

    public void deposit(String accountNumber, long amount, String transactionId) {

        Account account = accountService.getAccount(accountNumber);

        validateTransaction(amount, transactionId);

        long newBalance = Math.addExact(account.getBalance(), amount);

        if (!trustGuard.isValidTransaction(amount, transactionId)) {
            throw new IllegalArgumentException("Duplicate transaction!");
        }

        account.setBalance(newBalance);

        record(accountNumber, "DEPOSIT", amount, null);
    }

    public void withdraw(String accountNumber, long amount, String transactionId) {

        Account account = accountService.getAccount(accountNumber);

        validateTransaction(amount, transactionId);

        if (account.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance!");
        }

        if (!trustGuard.isValidTransaction(amount, transactionId)) {
            throw new IllegalArgumentException("Duplicate transaction!");
        }

        account.setBalance(account.getBalance() - amount);

        record(accountNumber, "WITHDRAWAL", amount, null);
    }

    public void transfer(String senderNumber, String receiverNumber,
                         long amount, String transactionId) {

        if (senderNumber.equals(receiverNumber)) {
            throw new IllegalArgumentException("Cannot transfer to same account!");
        }

        Account sender = accountService.getAccount(senderNumber);
        Account receiver = accountService.getAccount(receiverNumber);

        validateTransaction(amount, transactionId);

        if (sender.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient balance!");
        }

        long receiverBalance = Math.addExact(receiver.getBalance(), amount);

        if (!trustGuard.isValidTransaction(amount, transactionId)) {
            throw new IllegalArgumentException("Duplicate transaction!");
        }

        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiverBalance);

        record(senderNumber, "TRANSFER_OUT", amount, receiverNumber);
        record(receiverNumber, "TRANSFER_IN", amount, senderNumber);
    }

    private void validateTransaction(long amount, String transactionId) {
        if (!ValidationUtil.isValidAmount(amount)) {
            throw new IllegalArgumentException("Amount must be positive!");
        }

        if (transactionId == null || transactionId.isBlank()) {
            throw new IllegalArgumentException("Transaction ID required!");
        }
    }

    private void record(String accountNumber, String type,
                        long amount, String relatedAccount) {

        String id = java.util.UUID.randomUUID().toString();

        transactions.add(new Transaction(
            id,
            accountNumber,
            type,
            amount,
            relatedAccount,
            type.replace("_", " "),
            LocalDateTime.now().toString()
        ));
    }

    public List<Transaction> getTransactionHistory(String accountNumber) {
        List<Transaction> history = new ArrayList<>();

        for (Transaction transaction : transactions) {
            if (transaction.getAccountNumber().equals(accountNumber)) {
                history.add(transaction);
            }
        }

        return history;
    }

public long getBalance(String accountNumber) {
    return accountService.getAccount(accountNumber).getBalance();
}

public List<Transaction> generateStatement(
        String accountNumber,
        java.time.LocalDate startDate,
        java.time.LocalDate endDate) {

    if (startDate == null || endDate == null ||
        endDate.isBefore(startDate)) {
        throw new IllegalArgumentException("Invalid date range");
    }

    List<Transaction> statement = new ArrayList<>();

    for (Transaction transaction : getTransactionHistory(accountNumber)) {
        java.time.LocalDate date =
            java.time.LocalDate.parse(
                transaction.getTransactionDate().substring(0, 10));

        if (!date.isBefore(startDate) && !date.isAfter(endDate)) {
            statement.add(transaction);
        }
    }

    return statement;
}

}
