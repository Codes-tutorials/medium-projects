package com.example.encryption;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class EncryptionDemoApplicationTests {

    @Test
    void contextLoads() {
        // This test ensures that the Spring context loads successfully
        assertThat(true).isTrue();
    }

    @Test
    void applicationStartsSuccessfully() {
        // This test verifies that the application can start without errors
        // The @SpringBootTest annotation will start the full application context
        assertThat(true).isTrue();
    }
}