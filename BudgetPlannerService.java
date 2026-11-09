package com.novara.service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class BudgetPlannerService {

    private final Map<String, Long> budget = new LinkedHashMap<>();
    private final Map<String, Long> spent = new LinkedHashMap<>();
    private long totalAmount;

    private static final String[] CATEGORIES = {
        "Savings", "Food", "Shopping", "Travel",
        "Education", "Entertainment", "Emergency"
    };

    public Map<String, Long> createRecommendedBudget(
            long amount, Set<String> interests, int savingsPercent) {

        if (amount <= 0 || savingsPercent < 10 || savingsPercent > 70) {
            throw new IllegalArgumentException("Invalid amount or savings goal.");
        }

        interests = interests == null ? Set.of() : interests;

        int remainingPercent = 100 - savingsPercent;

        int[] weights = {0, 25, 15, 10, 15, 10, 25};

        for (int i = 1; i < CATEGORIES.length; i++) {
            if (interests.contains(CATEGORIES[i])) {
                weights[i] += 10;
            }
        }

        int totalWeight = 0;
        for (int i = 1; i < weights.length; i++) {
            totalWeight += weights[i];
        }

        Map<String, Long> result = new LinkedHashMap<>();
        result.put("Savings", amount * savingsPercent / 100);

        long distributed = result.get("Savings");

        for (int i = 1; i < CATEGORIES.length; i++) {
            long allocation = (amount * remainingPercent * weights[i])
                    / (100L * totalWeight);

            result.put(CATEGORIES[i], allocation);
            distributed += allocation;
        }

        // Assign rounding difference to emergency.
        result.put("Emergency",
                result.get("Emergency") + amount - distributed);

        setBudget(amount, result);
        return new LinkedHashMap<>(budget);
    }

    public Map<String, Long> createCustomBudget(
            long amount, Map<String, Long> allocations) {

        if (amount <= 0 || allocations == null || allocations.isEmpty()) {
            throw new IllegalArgumentException("Invalid budget.");
        }

        long sum = 0;

        for (Map.Entry<String, Long> entry : allocations.entrySet()) {
            if (entry.getValue() == null || entry.getValue() < 0) {
                throw new IllegalArgumentException("Invalid allocation.");
            }
            sum = Math.addExact(sum, entry.getValue());
        }

        if (sum > amount) {
            throw new IllegalArgumentException(
                    "Allocations exceed available money.");
        }

        Map<String, Long> result = new LinkedHashMap<>(allocations);
        result.putIfAbsent("Savings", 0L);
        result.put("Savings", result.get("Savings") + amount - sum);

        setBudget(amount, result);
        return new LinkedHashMap<>(budget);
    }

    private void setBudget(long amount, Map<String, Long> allocations) {
        totalAmount = amount;
        budget.clear();
        spent.clear();

        budget.putAll(allocations);

        for (String category : budget.keySet()) {
            spent.put(category, 0L);
        }
    }

    public void recordSpending(String category, long amount) {
        if (!budget.containsKey(category) || amount <= 0) {
            throw new IllegalArgumentException("Invalid category or amount.");
        }

        long newSpent = Math.addExact(spent.get(category), amount);

        if (newSpent > budget.get(category)) {
            throw new IllegalArgumentException(
                    "Spending exceeds the category budget.");
        }

        spent.put(category, newSpent);
    }

    public long getRemaining(String category) {
        return budget.getOrDefault(category, 0L)
                - spent.getOrDefault(category, 0L);
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public long getTotalSpent() {
        return spent.values().stream().mapToLong(Long::longValue).sum();
    }

    public long getTotalRemaining() {
        return totalAmount - getTotalSpent();
    }

    public Map<String, Long> getBudget() {
        return new LinkedHashMap<>(budget);
    }

    public Map<String, Long> getSpent() {
        return new LinkedHashMap<>(spent);
    }
}