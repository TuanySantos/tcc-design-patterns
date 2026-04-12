package withpatterns.dto;

public record UserRequest(
        String name,
        String email,
        String password,
        String cpf
) {}