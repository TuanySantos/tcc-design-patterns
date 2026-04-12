package withpatterns.strategy;

import org.junit.jupiter.api.Test;
import withpatterns.strategy.impl.CpfValidationStrategy;
import withpatterns.strategy.impl.EmailValidationStrategy;
import withpatterns.strategy.impl.PasswordValidationStrategy;

import static org.junit.jupiter.api.Assertions.*;

class ValidationStrategiesTest {

    @Test
    void emailStrategy_shouldValidate() {
        var s = new EmailValidationStrategy();
        assertTrue(s.validate("a@b.com"));
        assertFalse(s.validate("invalido"));
        assertFalse(s.validate(null));
    }

    @Test
    void cpfStrategy_shouldValidate() {
        var s = new CpfValidationStrategy();
        assertTrue(s.validate("12345678901"));
        assertFalse(s.validate("123"));
        assertFalse(s.validate("abcdefghijk"));
        assertFalse(s.validate(null));
    }

    @Test
    void passwordStrategy_shouldValidate() {
        var s = new PasswordValidationStrategy();
        assertTrue(s.validate("Senha123"));
        assertFalse(s.validate("abc"));
        assertFalse(s.validate("senhasemnumero"));
        assertFalse(s.validate(null));
    }
}