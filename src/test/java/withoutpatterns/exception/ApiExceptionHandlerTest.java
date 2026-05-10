package withoutpatterns.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class ApiExceptionHandlerCoverageTest {

    private final ApiExceptionHandler handler = new ApiExceptionHandler();

    @Test
    void shouldInvokeAllExceptionHandlerMethods() throws Exception {
        for (Method m : ApiExceptionHandler.class.getDeclaredMethods()) {
            if (!m.isAnnotationPresent(ExceptionHandler.class)) continue;

            m.setAccessible(true);

            Object[] args = new Object[m.getParameterCount()];
            Class<?>[] types = m.getParameterTypes();

            for (int i = 0; i < types.length; i++) {
                args[i] = createArg(types[i]);
            }

            Object result = m.invoke(handler, args);

            if (result instanceof ResponseEntity<?> re) {
                assertTrue(re.getStatusCode().value() >= 400);
                assertNotNull(re.getBody());
            }
        }
    }

    private Object createArg(Class<?> type) {
        if (type.equals(ValidationException.class)) return new ValidationException("erro validação");
        if (type.equals(IllegalArgumentException.class)) return new IllegalArgumentException("argumento inválido");
        if (type.equals(RuntimeException.class)) return new RuntimeException("boom");
        if (type.equals(Exception.class)) return new Exception("boom");
        return null;
    }
}
