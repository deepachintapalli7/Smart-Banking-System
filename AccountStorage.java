package com.novara.storage;

import com.novara.model.Account;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import java.io.StringReader;
import java.io.StringWriter;

public class AccountStorage {

    private final FileStorage storage = new FileStorage();

    public void saveAccount(Account account) throws IOException {

        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null");
        }

        Properties data = new Properties();
        data.setProperty("accountNumber", account.getAccountNumber());
        data.setProperty("customerId", account.getCustomerId());
        data.setProperty("accountType", account.getAccountType());
        data.setProperty("balance", String.valueOf(account.getBalance()));

        StringWriter writer = new StringWriter();
        data.store(writer, "Account Details");

        Path path = Path.of("data", "accounts",
                account.getAccountNumber() + ".txt");

        storage.saveFile(path, writer.toString());
    }

    public Account findAccount(String accountNumber) throws IOException {

        Path path = Path.of("data", "accounts",
                accountNumber + ".txt");

        if (!Files.exists(path)) {
            return null;
        }

        Properties data = new Properties();
        data.load(new StringReader(storage.readFile(path)));

        return new Account(
                data.getProperty("accountNumber"),
                data.getProperty("customerId"),
                data.getProperty("accountType"),
                Long.parseLong(data.getProperty("balance"))
        );
    }

    public void updateBalance(String accountNumber, long balance)
            throws IOException {

        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }

        Account account = findAccount(accountNumber);

        if (account == null) {
            throw new IOException("Account not found");
        }

        account.setBalance(balance);
        saveUpdatedAccount(account);
    }

    private void saveUpdatedAccount(Account account) throws IOException {

        Properties data = new Properties();
        data.setProperty("accountNumber", account.getAccountNumber());
        data.setProperty("customerId", account.getCustomerId());
        data.setProperty("accountType", account.getAccountType());
        data.setProperty("balance", String.valueOf(account.getBalance()));

        StringWriter writer = new StringWriter();
        data.store(writer, "Account Details");

        Path path = Path.of("data", "accounts",
                account.getAccountNumber() + ".txt");

        storage.updateFile(path, writer.toString());
    }
}
