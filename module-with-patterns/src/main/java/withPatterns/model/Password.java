package withPatterns.model;

import lombok.Getter;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class Password {

    private final String password;

    public Password(String password) {
        if (password == null || password.length() < 8 || !password.matches(".*[A-Z].*") ||
                !password.matches(".*[a-z].*") || !password.matches(".*\\d.*")) {
            throw new IllegalArgumentException("A senha deve ter pelo menos 8 caracteres, uma letra maiúscula, uma minúscula e um número.");
        }
        this.password = password;
    }
}
