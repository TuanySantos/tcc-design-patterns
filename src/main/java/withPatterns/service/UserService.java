package withPatterns.service;

import lombok.Getter;
import lombok.Setter;
import withPatterns.model.User;
import withPatterns.repository.UserRepository;
import withPatterns.strategy.ValidationStrategy;
import withPatterns.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import withPatterns.strategy.impl.PasswordValidationStrategy;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Configura a estratégia de validação
    @Setter
    private ValidationStrategy validationStrategy;

    @Getter
    private PasswordValidationStrategy passwordValidationStrategy;

    public User save(User user) {
        // Aplica a estratégia de validação antes de salvar
        if (validationStrategy != null && !validationStrategy.validate(String.valueOf(user.getEmail()))) {
            throw new ValidationException("Email inválido");
        }
        // Valida a senha do usuário antes de salvar
        if (!passwordValidationStrategy.validate(String.valueOf(user.getPassword()))) {
            throw new ValidationException("A senha deve ter pelo menos 8 caracteres, uma letra maiúscula, uma minúscula e um número.");
        }
        return userRepository.save(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User update(User user) {
        return userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

}
