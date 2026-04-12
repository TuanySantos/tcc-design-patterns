package withpatterns.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ApiExceptionHandlerTest {

    private final ApiExceptionHandler handler = new ApiExceptionHandler();

    @Test
    void handleValidation_shouldReturn400() {
        ResponseEntity<Map<String, Object>> resp =
                handler.handleValidation(new ValidationException("CPF inválido"));

        assertEquals(400, resp.getStatusCode().value());
        assertNotNull(resp.getBody());
        assertEquals("CPF inválido", resp.getBody().get("message"));
    }

    @Test
    void handleGeneric_shouldReturn500() {
        ResponseEntity<Map<String, Object>> resp =
                handler.handleGeneric(new RuntimeException("boom"));

        assertEquals(500, resp.getStatusCode().value());
        assertNotNull(resp.getBody());
        assertEquals("Erro inesperado", resp.getBody().get("message"));
    }
}