package withpatterns.repository.impl;

import withpatterns.dto.UserRequest;
import withpatterns.dto.UserResponse;
import withpatterns.exception.ValidationException;
import withpatterns.repository.UserRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcUserRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserResponse save(UserRequest request) {
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

    @Override
    public Optional<UserResponse> findById(Long id) {
        String sql = "SELECT id, name, email, cpf FROM users WHERE id = ?";
        List<UserResponse> results = jdbcTemplate.query(sql, (rs, rowNum) ->
                new UserResponse(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("cpf")
                ), id);
        return results.stream().findFirst();
    }

    @Override
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

    @Override
    public UserResponse update(Long id, UserRequest request) {
        String sql = "UPDATE users SET name=?, email=?, password=?, cpf=? WHERE id=?";
        int updated = jdbcTemplate.update(sql,
                request.name(), request.email(), request.password(), request.cpf(), id);
        if (updated == 0) return null;
        return new UserResponse(id, request.name(), request.email(), request.cpf());
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM users WHERE id=?";
        int deleted = jdbcTemplate.update(sql, id);
        return deleted > 0;
    }
}