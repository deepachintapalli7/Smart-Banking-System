
package com.novara.service;

import java.util.HashSet;
import java.util.Set;

public class TrustGuardService {

    private final Set<String> processedTransactions = new HashSet<>();

    public boolean isDuplicate(String transactionId) {
        return !processedTransactions.add(transactionId);
    }

    public boolean isSuspicious(long amount) {
        return amount >= 50000000L; // ₹5 lakh
    }

    public boolean isValidTransaction(long amount, String transactionId) {
        return amount > 0 &&
               transactionId != null &&
               !transactionId.trim().isEmpty() &&
               !isDuplicate(transactionId);
    }
}
