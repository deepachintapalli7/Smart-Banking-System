
package com.novara.service;

import com.novara.model.Account;
import com.novara.util.AccountNumberGenerator;
import com.novara.util.ValidationUtil;

import java.util.HashMap;
import java.util.Map;

public class AccountService {

    private final Map<String, Account> accounts = new HashMap<>();

    public Account createAccount(String customerId, String accountType) {

        if (customerId == null || customerId.isBlank()) {
            throw new IllegalArgumentException("Invalid customer ID");
        }

        if (!ValidationUtil.isValidAccountType(accountType)) {
            throw new IllegalArgumentException("Invalid account type");
        }

        String accountNumber;

        do {
            accountNumber = AccountNumberGenerator.generateAccountNumber();
        } while (accounts.containsKey(accountNumber));

        Account account = new Account(
            accountNumber, customerId, accountType, 0L
        );

        accounts.put(accountNumber, account);

        return account;
    }

    public Account getAccount(String accountNumber) {
        Account account = accounts.get(accountNumber);

        if (account == null) {
            throw new IllegalArgumentException("Account not found");
        }

        return account;
    }

    public Map<String, Account> getAllAccounts() {
        return new HashMap<>(accounts);
    }
}
