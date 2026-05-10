package withoutpatterns;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = WithoutPatternsApplication.class)
class ContextLoadsTest {

    @Test
    void contextLoads() {
        // Só carrega o contexto do Spring
    }
}