package withPatterns.model;

import lombok.Getter;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class Email {

    private final String email;

    public Email(String email) {
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Email inválido");
        }
        this.email = email;
    }
}
