package withpatterns.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import withpatterns.dto.UserRequest;
import withpatterns.dto.UserResponse;
import withpatterns.exception.ValidationException;
import withpatterns.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserRepository repository;
    private UserService service;

    @BeforeEach
    void setUp() {
        repository = mock(UserRepository.class);
        service = new UserService(repository);
    }

    @Test
    void create_shouldValidate_andDelegate_andNotifyObservers() {
        var req = new UserRequest("Tuany", "tuany@exemplo.com", "Senha123", "12345678901");
        when(repository.save(req)).thenReturn(new UserResponse(1L, "Tuany", "tuany@exemplo.com", "12345678901"));

        var resp = service.create(req);

        assertEquals(1L, resp.id());
        verify(repository).save(req);
    }

    @Test
    void create_shouldThrowOnInvalidBranches() {
        assertThrows(ValidationException.class, () -> service.create(null));
        assertThrows(ValidationException.class, () -> service.create(new UserRequest("", "a@b.com", "Senha123", "12345678901")));
        assertThrows(ValidationException.class, () -> service.create(new UserRequest("A", "invalido", "Senha123", "12345678901")));
        assertThrows(ValidationException.class, () -> service.create(new UserRequest("A", "a@b.com", "abc", "12345678901")));
        assertThrows(ValidationException.class, () -> service.create(new UserRequest("A", "a@b.com", "Senha123", "123")));
        verifyNoInteractions(repository);
    }

    @Test
    void findById_shouldThrowWhenInvalidOrNotFound() {
        assertThrows(ValidationException.class, () -> service.findById(null));
        assertThrows(ValidationException.class, () -> service.findById(0L));

        when(repository.findById(10L)).thenReturn(Optional.empty());
        assertThrows(ValidationException.class, () -> service.findById(10L));
    }

    @Test
    void findAll_shouldReturnList() {
        when(repository.findAll()).thenReturn(List.of(new UserResponse(1L, "A", "a@b.com", "12345678901")));
        assertEquals(1, service.findAll().size());
    }

    @Test
    void update_shouldThrowWhenNotFound() {
        var req = new UserRequest("Tuany", "tuany@exemplo.com", "Senha123", "12345678901");
        when(repository.update(99L, req)).thenReturn(null);

        assertThrows(ValidationException.class, () -> service.update(99L, req));
    }

    @Test
    void delete_shouldThrowWhenNotFound() {
        when(repository.delete(99L)).thenReturn(false);
        assertThrows(ValidationException.class, () -> service.delete(99L));
    }
}