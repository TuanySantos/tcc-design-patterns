package withpatterns.dto;

public record UserResponse(
        Long id,
        String name,
        String email,
        String cpf
) {}