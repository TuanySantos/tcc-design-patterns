

package withpatterns.service;

import withpatterns.config.DatabaseConnectionSingleton;
import withpatterns.dto.UserRequest;
import withpatterns.dto.UserResponse;
import withpatterns.exception.ValidationException;
import withpatterns.observer.UserObserver;
import withpatterns.observer.impl.AuditLogObserver;
import withpatterns.observer.impl.EmailNotificationObserver;
import withpatterns.repository.UserRepository;
import withpatterns.strategy.ValidationStrategy;
import withpatterns.strategy.impl.CpfValidationStrategy;
import withpatterns.strategy.impl.EmailValidationStrategy;
import withpatterns.strategy.impl.PasswordValidationStrategy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private static final String INVALID_ID_MSG = "Id inválido";
    private static final String USER_NOT_FOUND_MSG = "Usuário não encontrado";

    private final UserRepository repository;

    // strategies
    private final ValidationStrategy emailStrategy = new EmailValidationStrategy();
    private final ValidationStrategy cpfStrategy = new CpfValidationStrategy();
    private final ValidationStrategy passwordStrategy = new PasswordValidationStrategy();

    // observers
    private final List<UserObserver> observers = new ArrayList<>();

    // singleton usage (didático): cria um JdbcTemplate a partir do DataSource do singleton
    @SuppressWarnings("unused")
    private final JdbcTemplate singletonJdbcTemplate =
            new JdbcTemplate(DatabaseConnectionSingleton.getInstance().getDataSource());

    public UserService(UserRepository repository) {
        this.repository = repository;

        // registra observers (poderia ser via DI, mas aqui é didático)
        observers.add(new AuditLogObserver());
        observers.add(new EmailNotificationObserver());
    }

    public UserResponse create(UserRequest request) {
        validate(request);
        UserResponse created = repository.save(request);
        notifyObservers("User created");
        return created;
    }

    public UserResponse findById(Long id) {
        if (id == null || id <= 0) throw new ValidationException(INVALID_ID_MSG);
        return repository.findById(id).orElseThrow(() -> new ValidationException(USER_NOT_FOUND_MSG));
    }

    public List<UserResponse> findAll() {
        return repository.findAll();
    }

    public UserResponse update(Long id, UserRequest request) {
        if (id == null || id <= 0) throw new ValidationException(INVALID_ID_MSG);
        validate(request);

        UserResponse updated = repository.update(id, request);
        if (updated == null) throw new ValidationException(USER_NOT_FOUND_MSG);

        notifyObservers("User updated: id=" + id);
        return updated;
    }

    public void delete(Long id) {
        if (id == null || id <= 0) throw new ValidationException(INVALID_ID_MSG);
        boolean deleted = repository.delete(id);
        if (!deleted) throw new ValidationException(USER_NOT_FOUND_MSG);
        notifyObservers("User deleted: id=" + id);
    }

    private void validate(UserRequest request) {
        if (request == null) throw new ValidationException("Payload inválido");

        if (request.name() == null || request.name().isBlank())
            throw new ValidationException("Nome é obrigatório");

        if (!emailStrategy.validate(request.email()))
            throw new ValidationException("Email inválido");

        if (!passwordStrategy.validate(request.password()))
            throw new ValidationException("Senha inválida: mínimo 8, 1 maiúscula, 1 minúscula e 1 número");

        if (!cpfStrategy.validate(request.cpf()))
            throw new ValidationException("CPF inválido: deve conter 11 dígitos numéricos");
    }

    private void notifyObservers(String message) {
        for (UserObserver observer : observers) {
            observer.update(message);
        }
    }
}