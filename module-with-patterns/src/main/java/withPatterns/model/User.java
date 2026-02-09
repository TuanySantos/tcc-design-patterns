package withPatterns.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class User {

    private final Long id;
    private final String name;
    private final Email email;
    private final Password password;
    private final Cpf cpf;

    // O construtor é necessário para garantir a imutabilidade
    public User(Long id, String name, Email email, Password password, Cpf cpf) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.cpf = cpf;
    }
}
