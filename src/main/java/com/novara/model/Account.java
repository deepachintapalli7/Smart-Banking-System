
package com.novara.model;

public class Account {

    private String accountNumber;
    private String customerId;
    private String accountType;
    private long balance;

    public Account(String accountNumber, String customerId,
                   String accountType, long balance) {
        this.accountNumber = accountNumber;
        this.customerId = customerId;
        this.accountType = accountType;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getAccountType() {
        return accountType;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }
}
