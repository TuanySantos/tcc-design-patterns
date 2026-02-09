package withPatterns.model;

import lombok.Getter;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class Cpf {

    private final String cpf;

    public Cpf(String cpf) {
        if (cpf == null || !cpf.matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF inválido. O CPF deve ter exatamente 11 dígitos.");
        }
        this.cpf = cpf;
    }
}
