
package com.novara.util;



public class ValidationUtil {

    public static boolean isValidName(String name) {
        return name != null && name.trim().length() >= 3;
    }

    public static boolean isValidUsername(String username) {
        return username != null &&
               username.matches("[a-zA-Z0-9_]{4,20}");
    }

    public static boolean isValidAmount(long amount) {
        return amount > 0;
    }

    public static boolean isValidAccountNumber(String accountNumber) {
        return accountNumber != null &&
               accountNumber.matches("[0-9]{10}");
    }

    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 8;
    }

    public static boolean isValidAccountType(String type) {
        return "SAVINGS".equals(type) ||
               "CURRENT".equals(type);
    }
}
