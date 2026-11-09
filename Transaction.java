
package com.novara.model;

public class Transaction {

    private String transactionId;
    private String accountNumber;
    private String transactionType;
    private long amount;
    private String relatedAccount;
    private String description;
    private String transactionDate;

    public Transaction(String transactionId, String accountNumber,
            String transactionType, long amount,
            String relatedAccount, String description,
            String transactionDate) {

        this.transactionId = transactionId;
        this.accountNumber = accountNumber;
        this.transactionType = transactionType;
        this.amount = amount;
        this.relatedAccount = relatedAccount;
        this.description = description;
        this.transactionDate = transactionDate;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public long getAmount() {
        return amount;
    }

    public String getRelatedAccount() {
        return relatedAccount;
    }

    public String getDescription() {
        return description;
    }

    public String getTransactionDate() {
        return transactionDate;
    }
}
