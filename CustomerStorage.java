package com.novara.storage;

import com.novara.model.Customer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.ArrayList;

public class CustomerStorage {

    private final Path directory = Path.of("data", "customers");

    public void saveCustomer(Customer customer) throws IOException {

        if (customer == null) {
            throw new IllegalArgumentException("Customer is null");
        }

        Files.createDirectories(directory);

        Path path = directory.resolve(
            customer.getCustomerId() + ".txt"
        );

        if (Files.exists(path)) {
            throw new IOException("Customer already exists");
        }

        List<String> details = new ArrayList<>();

        details.add(customer.getCustomerId());
        details.add(customer.getFullName());
        details.add(customer.getUsername());
        details.add(customer.getPasswordHash());

        Files.write(path, details);
    }

    public Customer findCustomer(String customerId)
            throws IOException {

        Path path = directory.resolve(customerId + ".txt");

        if (!Files.exists(path)) {
            return null;
        }

        List<String> details = Files.readAllLines(path);

        if (details.size() != 4) {
            throw new IOException("Invalid customer file");
        }

        return new Customer(
            details.get(0),
            details.get(1),
            details.get(2),
            details.get(3)
        );
    }
}