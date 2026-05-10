package withoutpatterns.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import withoutpatterns.dto.UserRequest;
import withoutpatterns.dto.UserResponse;
import withoutpatterns.exception.ValidationException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private JdbcTemplate jdbcTemplate;
    private UserService service;

    @BeforeEach
    void setUp() {
        jdbcTemplate = mock(JdbcTemplate.class);
        service = new UserService(jdbcTemplate);
    }

    @Test
    void create_shouldInsertAndReturnId() {
        var req = new UserRequest("Tuany", "tuany@exemplo.com", "Senha123", "12345678901");

        when(jdbcTemplate.update(any(PreparedStatementCreator.class), any(KeyHolder.class)))
                .thenAnswer(inv -> {
                    KeyHolder kh = inv.getArgument(1);
                    if (kh instanceof GeneratedKeyHolder gkh) {
                        // Simula chave gerada
                        gkh.getKeyList().add(Collections.singletonMap("ID", 10L));
                    }
                    return 1;
                });

        UserResponse resp = service.create(req);

        assertNotNull(resp);
        assertEquals(10L, resp.id());
        assertEquals("Tuany", resp.name());
        assertEquals("tuany@exemplo.com", resp.email());
        assertEquals("12345678901", resp.cpf());

        verify(jdbcTemplate, times(1))
                .update(any(PreparedStatementCreator.class), any(KeyHolder.class));
    }

    @Test
    void create_shouldThrowWhenIdNotGenerated() {
        var req = new UserRequest("Tuany", "tuany@exemplo.com", "Senha123", "12345678901");

        when(jdbcTemplate.update(any(PreparedStatementCreator.class), any(KeyHolder.class)))
                .thenReturn(1);

        assertThrows(ValidationException.class, () -> service.create(req));
    }

    @Test
    void create_shouldCoverValidationBranches() {
        assertThrows(ValidationException.class, () -> service.create(null));
        assertThrows(ValidationException.class, () -> service.create(new UserRequest("", "a@b.com", "Senha123", "12345678901")));
        assertThrows(ValidationException.class, () -> service.create(new UserRequest("A", "invalido", "Senha123", "12345678901")));
        assertThrows(ValidationException.class, () -> service.create(new UserRequest("A", "a@b.com", "abc", "12345678901")));
        assertThrows(ValidationException.class, () -> service.create(new UserRequest("A", "a@b.com", "Senha123", "123")));

        verifyNoInteractions(jdbcTemplate);
    }

    @Test
    void findById_shouldThrowOnInvalidId() {
        assertThrows(ValidationException.class, () -> service.findById(null));
        assertThrows(ValidationException.class, () -> service.findById(0L));
        assertThrows(ValidationException.class, () -> service.findById(-1L));
    }

    @Test
    void findAll_shouldReturnList() {
        when(jdbcTemplate.query(anyString(), any(RowMapper.class)))
                .thenReturn(List.of(new UserResponse(1L, "A", "a@b.com", "12345678901")));
        var list = service.findAll();
        assertEquals(1, list.size());
    }

    @Test
    void update_shouldReturnUser_whenUpdated() {
        var req = new UserRequest("A", "a@b.com", "Senha123", "12345678901");
        when(jdbcTemplate.update(anyString(), any(Object[].class))).thenReturn(1);
        var resp = service.update(1L, req);
        assertEquals(1L, resp.id());
    }

    @Test
    void delete_shouldNotThrow_whenDeleted() {
        when(jdbcTemplate.update(anyString(), any(Object[].class))).thenReturn(1);
        assertDoesNotThrow(() -> service.delete(1L));
    }

    @Test
    void update_shouldThrowWhenInvalidId() {
        var req = new UserRequest("A", "a@b.com", "Senha123", "12345678901");
        assertThrows(ValidationException.class, () -> service.update(null, req));
        assertThrows(ValidationException.class, () -> service.update(0L, req));
        verifyNoInteractions(jdbcTemplate);
    }

    @Test
    void update_shouldThrowWhenUserNotFound() {
        var req = new UserRequest("A", "a@b.com", "Senha123", "12345678901");

        // UPDATE retorna 0 => usuário não encontrado
        when(jdbcTemplate.update(anyString(), any(), any(), any(), any(), any()))
                .thenReturn(0);

        assertThrows(ValidationException.class, () -> service.update(99L, req));
    }

    @Test
    void delete_shouldThrowWhenInvalidOrNotFound() {
        assertThrows(ValidationException.class, () -> service.delete(null));
        assertThrows(ValidationException.class, () -> service.delete(0L));
        when(jdbcTemplate.update(anyString(), any(Object[].class)))
                .thenReturn(0);
        assertThrows(ValidationException.class, () -> service.delete(99L));
    }

    @Test
    void findById_shouldReturnUser_whenFound() {
        // Caso seu findById use queryForObject(String, RowMapper, Object...)
        doReturn(new UserResponse(10L, "A", "a@b.com", "12345678901"))
                .when(jdbcTemplate)
                .queryForObject(anyString(), any(RowMapper.class), anyLong());

        var resp = service.findById(10L);
        assertNotNull(resp);
        assertEquals(10L, resp.id());
    }

    @Test
    void findAll_shouldReturnList_robustOverloads() {
        var expected = List.of(new UserResponse(1L, "A", "a@b.com", "12345678901"));

        // cobre as overloads mais comuns de query(...)
        doReturn(expected)
                .when(jdbcTemplate)
                .query(anyString(), any(RowMapper.class));

        doReturn(expected)
                .when(jdbcTemplate)
                .query(anyString(), any(Object[].class), any(RowMapper.class));

        doReturn(expected)
                .when(jdbcTemplate)
                .query(anyString(), any(PreparedStatementSetter.class), any(RowMapper.class));

        var list = service.findAll();
        assertEquals(1, list.size());
    }

}