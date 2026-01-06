package com.example.encryption;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.example.encryption.config.EncryptionProperties;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

/**
 * Spring Boot API Encryption Demo Application
 * 
 * This application demonstrates three different approaches for implementing
 * API data encryption and decryption in Spring Boot:
 * 1. AOP-based transparent encryption/decryption
 * 2. Global filter implementation
 * 3. Custom MessageConverter approach
 * 
 * @author Spring Boot Encryption Demo
 * @version 1.0.0
 */
@SpringBootApplication
@EnableConfigurationProperties(EncryptionProperties.class)
@OpenAPIDefinition(
    info = @Info(
        title = "Spring Boot API Encryption Demo",
        version = "1.0.0",
        description = "Comprehensive demonstration of API data encryption/decryption approaches in Spring Boot",
        contact = @Contact(
            name = "API Support",
            email = "support@example.com",
            url = "https://github.com/yourusername/spring-boot-api-encryption"
        ),
        license = @License(
            name = "MIT License",
            url = "https://opensource.org/licenses/MIT"
        )
    )
)
public class EncryptionDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(EncryptionDemoApplication.class, args);
        
        System.out.println("\n" +
            "🔐 Spring Boot API Encryption Demo Started Successfully!\n" +
            "📚 Swagger UI: http://localhost:8080/swagger-ui.html\n" +
            "📖 API Docs: http://localhost:8080/v3/api-docs\n" +
            "🏥 Health Check: http://localhost:8080/actuator/health\n" +
            "🎯 Ready to demonstrate three encryption approaches!\n"
        );
    }
}