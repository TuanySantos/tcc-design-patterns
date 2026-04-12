package withoutpatterns.service;


import withoutpatterns.dto.UserRequest;
import withoutpatterns.dto.UserResponse;
import withoutpatterns.exception.ValidationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Service
public class UserService {

    private final JdbcTemplate jdbcTemplate;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private static final String INVALID_ID_MSG = "Id inválido";

    public UserService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public UserResponse create(UserRequest request) {
        validate(request);

        logger.info("[WITHOUT] Creating user: {}", request.email());

        String sql = "INSERT INTO users(name, email, password, cpf) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, request.name());
            ps.setString(2, request.email());
            ps.setString(3, request.password());
            ps.setString(4, request.cpf());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key == null) {
            throw new ValidationException("Não foi possível obter o ID gerado");
        }
        Long id = key.longValue();
        return new UserResponse(id, request.name(), request.email(), request.cpf());
    }

    public UserResponse findById(Long id) {
        if (id == null || id <= 0) throw new ValidationException(INVALID_ID_MSG);

        String sql = "SELECT id, name, email, cpf FROM users WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                new UserResponse(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("cpf")
                ), id);
    }

    public List<UserResponse> findAll() {
        String sql = "SELECT id, name, email, cpf FROM users ORDER BY id";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new UserResponse(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("cpf")
                ));
    }

    public UserResponse update(Long id, UserRequest request) {
        if (id == null || id <= 0) throw new ValidationException(INVALID_ID_MSG);
        validate(request);

        logger.info("[WITHOUT] Updating user id={}", id);

        String sql = "UPDATE users SET name=?, email=?, password=?, cpf=? WHERE id=?";
        int updated = jdbcTemplate.update(sql,
                request.name(), request.email(), request.password(), request.cpf(), id);

        if (updated == 0) throw new ValidationException("Usuário não encontrado");
        return new UserResponse(id, request.name(), request.email(), request.cpf());
    }

    public void delete(Long id) {
        if (id == null || id <= 0) throw new ValidationException(INVALID_ID_MSG);

        logger.info("[WITHOUT] Deleting user id={}", id);

        String sql = "DELETE FROM users WHERE id=?";
        int deleted = jdbcTemplate.update(sql, id);
        if (deleted == 0) throw new ValidationException("Usuário não encontrado");
    }

    // Regras (iguais às do projeto com patterns para garantir equivalência)
    private void validate(UserRequest request) {
        if (request == null) throw new ValidationException("Payload inválido");

        if (request.name() == null || request.name().isBlank())
            throw new ValidationException("Nome é obrigatório");

        if (!isValidEmail(request.email()))
            throw new ValidationException("Email inválido");

        if (!isValidPassword(request.password()))
            throw new ValidationException("Senha inválida: mínimo 8, 1 maiúscula, 1 minúscula e 1 número");

        if (!isValidCpf(request.cpf()))
            throw new ValidationException("CPF inválido: deve conter 11 dígitos numéricos");
    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    private boolean isValidCpf(String cpf) {
        return cpf != null && cpf.matches("\\d{11}");
    }

    private boolean isValidPassword(String password) {
        if (password == null || password.length() < 8) return false;
        boolean hasUpper = password.chars().anyMatch(Character::isUpperCase);
        boolean hasLower = password.chars().anyMatch(Character::isLowerCase);
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);
        return hasUpper && hasLower && hasDigit;
    }
}