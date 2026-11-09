
package com.novara.model;

public class Customer {

    private String customerId;
    private String fullName;
    private String username;
    private String passwordHash;
    private String createdAt;

    public Customer(String customerId, String fullName,
                    String username, String passwordHash,
                    String createdAt) {
        this.customerId = customerId;
        this.fullName = fullName;
        this.username = username;
        this.passwordHash = passwordHash;
        this.createdAt = createdAt;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
