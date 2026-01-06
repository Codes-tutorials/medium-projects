# Contributing to Spring Boot API Encryption Demo

Thank you for your interest in contributing to this project! We welcome contributions from the community.

## 📋 Table of Contents

- [Code of Conduct](#code-of-conduct)
- [Getting Started](#getting-started)
- [How to Contribute](#how-to-contribute)
- [Development Setup](#development-setup)
- [Coding Standards](#coding-standards)
- [Testing Guidelines](#testing-guidelines)
- [Pull Request Process](#pull-request-process)
- [Issue Reporting](#issue-reporting)

## 📜 Code of Conduct

This project adheres to a code of conduct. By participating, you are expected to uphold this code. Please report unacceptable behavior to the project maintainers.

## 🚀 Getting Started

1. **Fork the repository** on GitHub
2. **Clone your fork** locally:
   ```bash
   git clone https://github.com/yourusername/spring-boot-api-encryption.git
   cd spring-boot-api-encryption
   ```
3. **Create a branch** for your feature or bug fix:
   ```bash
   git checkout -b feature/your-feature-name
   ```

## 🤝 How to Contribute

### Types of Contributions

- **Bug fixes** - Fix issues in the existing code
- **New features** - Add new encryption algorithms or approaches
- **Documentation** - Improve README, code comments, or add examples
- **Performance improvements** - Optimize existing code
- **Security enhancements** - Improve security aspects
- **Tests** - Add or improve test coverage

### Areas for Contribution

1. **Additional Encryption Algorithms**
   - Implement ChaCha20-Poly1305
   - Add Elliptic Curve Cryptography (ECC)
   - Support for hybrid encryption schemes

2. **New Implementation Approaches**
   - WebSocket encryption support
   - GraphQL encryption integration
   - Reactive streams encryption

3. **Security Enhancements**
   - Key rotation mechanisms
   - Hardware Security Module (HSM) integration
   - Certificate-based authentication

4. **Performance Optimizations**
   - Async encryption processing
   - Connection pooling for external services
   - Caching strategies

5. **Documentation & Examples**
   - More comprehensive examples
   - Performance benchmarks
   - Security best practices guide

## 🛠 Development Setup

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- Git
- IDE (IntelliJ IDEA, Eclipse, or VS Code)

### Setup Steps

1. **Install dependencies:**
   ```bash
   mvn clean install
   ```

2. **Run tests:**
   ```bash
   mvn test
   ```

3. **Start the application:**
   ```bash
   ./scripts/run.sh dev
   ```

4. **Verify setup:**
   - Visit http://localhost:8080/swagger-ui.html
   - Check health endpoint: http://localhost:8080/actuator/health

## 📝 Coding Standards

### Java Code Style

- Follow [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)
- Use meaningful variable and method names
- Add JavaDoc comments for public methods and classes
- Keep methods small and focused (max 50 lines)
- Use proper exception handling

### Code Formatting

```java
// Good example
public class EncryptionService {
    
    private static final Logger logger = LoggerFactory.getLogger(EncryptionService.class);
    
    /**
     * Encrypts the given data using AES algorithm
     * 
     * @param data the data to encrypt
     * @return encrypted data as Base64 string
     * @throws EncryptionException if encryption fails
     */
    public String encrypt(String data) throws EncryptionException {
        if (data == null || data.isEmpty()) {
            throw new EncryptionException("Data cannot be null or empty");
        }
        
        try {
            // Implementation here
            return encryptedData;
        } catch (Exception e) {
            logger.error("Encryption failed", e);
            throw new EncryptionException("Failed to encrypt data", e);
        }
    }
}
```

### Package Structure

```
com.example.encryption/
├── annotation/          # Custom annotations
├── aspect/             # AOP aspects
├── config/             # Configuration classes
├── controller/         # REST controllers
├── dto/               # Data transfer objects
├── exception/         # Custom exceptions
├── filter/            # Servlet filters
├── service/           # Business services
└── util/              # Utility classes
```

## 🧪 Testing Guidelines

### Test Categories

1. **Unit Tests** - Test individual components in isolation
2. **Integration Tests** - Test component interactions
3. **Performance Tests** - Benchmark encryption/decryption operations
4. **Security Tests** - Validate security aspects

### Test Naming Convention

```java
// Pattern: methodName_scenario_expectedResult
@Test
void encrypt_withValidData_returnsEncryptedString() {
    // Test implementation
}

@Test
void decrypt_withInvalidData_throwsEncryptionException() {
    // Test implementation
}
```

### Test Coverage

- Maintain minimum 80% code coverage
- Focus on critical paths and edge cases
- Include negative test cases
- Test security-related functionality thoroughly

### Example Test

```java
@SpringBootTest
@ActiveProfiles("test")
class AESUtilsTest {

    @Autowired
    private AESUtils aesUtils;

    @Test
    void encrypt_withValidData_returnsEncryptedString() {
        // Given
        String originalData = "Hello, World!";
        
        // When
        String encryptedData = aesUtils.encrypt(originalData);
        
        // Then
        assertThat(encryptedData).isNotNull();
        assertThat(encryptedData).isNotEqualTo(originalData);
        assertThat(encryptedData).isBase64();
    }

    @Test
    void decrypt_withEncryptedData_returnsOriginalString() {
        // Given
        String originalData = "Hello, World!";
        String encryptedData = aesUtils.encrypt(originalData);
        
        // When
        String decryptedData = aesUtils.decrypt(encryptedData);
        
        // Then
        assertThat(decryptedData).isEqualTo(originalData);
    }
}
```

## 🔄 Pull Request Process

### Before Submitting

1. **Update documentation** if needed
2. **Add tests** for new functionality
3. **Run all tests** and ensure they pass
4. **Check code coverage** meets requirements
5. **Update CHANGELOG.md** with your changes

### PR Checklist

- [ ] Code follows the project's coding standards
- [ ] Tests are added and passing
- [ ] Documentation is updated
- [ ] No merge conflicts
- [ ] PR description clearly explains the changes
- [ ] Related issues are referenced

### PR Template

```markdown
## Description
Brief description of changes made.

## Type of Change
- [ ] Bug fix
- [ ] New feature
- [ ] Documentation update
- [ ] Performance improvement
- [ ] Security enhancement

## Testing
- [ ] Unit tests added/updated
- [ ] Integration tests added/updated
- [ ] Manual testing performed

## Checklist
- [ ] Code follows style guidelines
- [ ] Self-review completed
- [ ] Documentation updated
- [ ] Tests pass locally
```

## 🐛 Issue Reporting

### Bug Reports

When reporting bugs, please include:

1. **Environment details** (OS, Java version, Maven version)
2. **Steps to reproduce** the issue
3. **Expected behavior** vs **actual behavior**
4. **Error messages** or stack traces
5. **Sample code** if applicable

### Feature Requests

For feature requests, please provide:

1. **Clear description** of the proposed feature
2. **Use case** or problem it solves
3. **Proposed implementation** approach (if any)
4. **Alternatives considered**

### Issue Labels

- `bug` - Something isn't working
- `enhancement` - New feature or request
- `documentation` - Improvements or additions to documentation
- `good first issue` - Good for newcomers
- `help wanted` - Extra attention is needed
- `security` - Security-related issues

## 🏆 Recognition

Contributors will be recognized in:

- **README.md** - Contributors section
- **CHANGELOG.md** - Release notes
- **GitHub releases** - Release descriptions

## 📞 Getting Help

- **GitHub Issues** - For bugs and feature requests
- **GitHub Discussions** - For questions and general discussion
- **Email** - For security-related issues: security@example.com

## 📚 Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Java Cryptography Architecture](https://docs.oracle.com/javase/8/docs/technotes/guides/security/crypto/CryptoSpec.html)
- [OWASP Cryptographic Storage Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/Cryptographic_Storage_Cheat_Sheet.html)

Thank you for contributing to the Spring Boot API Encryption Demo project! 🎉