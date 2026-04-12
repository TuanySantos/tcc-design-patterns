package withpatterns.repository;

import withpatterns.dto.UserRequest;
import withpatterns.dto.UserResponse;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    UserResponse save(UserRequest request);
    Optional<UserResponse> findById(Long id);
    List<UserResponse> findAll();
    UserResponse update(Long id, UserRequest request);
    boolean delete(Long id);
}