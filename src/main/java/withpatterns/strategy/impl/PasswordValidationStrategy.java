package withpatterns.strategy.impl;

import withpatterns.strategy.ValidationStrategy;

public class PasswordValidationStrategy implements ValidationStrategy {

    @Override
    public boolean validate(String password) {
        if (password == null || password.length() < 8) return false;
        boolean hasUpper = password.chars().anyMatch(Character::isUpperCase);
        boolean hasLower = password.chars().anyMatch(Character::isLowerCase);
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);
        return hasUpper && hasLower && hasDigit;
    }
}