package com.novara.storage;

import com.novara.model.Account;
import java.io.IOException;

public class StorageTest {

    public static void main(String[] args) {

        try {
            AccountStorage accountStorage = new AccountStorage();

            String id = "ACC" + System.currentTimeMillis();

            Account account = new Account(
                    id, "CUST001", "Savings", 5000
            );

            accountStorage.saveAccount(account);

            accountStorage.updateBalance(id, 7000);

            Account updated = accountStorage.findAccount(id);

            System.out.println("Balance updated successfully!");
            System.out.println("New Balance: " + updated.getBalance());

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}