package com.example.encryption.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Configuration properties for encryption settings
 */
@ConfigurationProperties(prefix = "encryption")
@Validated
public class EncryptionProperties {

    private AesProperties aes = new AesProperties();
    private RsaProperties rsa = new RsaProperties();
    private List<String> enabledPaths = List.of("/api/v1/**");
    private List<String> excludedPaths = List.of("/actuator/**", "/swagger-ui/**");
    private boolean debugMode = false;

    // Getters and Setters
    public AesProperties getAes() {
        return aes;
    }

    public void setAes(AesProperties aes) {
        this.aes = aes;
    }

    public RsaProperties getRsa() {
        return rsa;
    }

    public void setRsa(RsaProperties rsa) {
        this.rsa = rsa;
    }

    public List<String> getEnabledPaths() {
        return enabledPaths;
    }

    public void setEnabledPaths(List<String> enabledPaths) {
        this.enabledPaths = enabledPaths;
    }

    public List<String> getExcludedPaths() {
        return excludedPaths;
    }

    public void setExcludedPaths(List<String> excludedPaths) {
        this.excludedPaths = excludedPaths;
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    /**
     * AES encryption properties
     */
    public static class AesProperties {
        @NotBlank(message = "AES key cannot be blank")
        private String key = "MySecretKey12345";
        
        @NotBlank(message = "AES algorithm cannot be blank")
        private String algorithm = "AES/ECB/PKCS5Padding";
        
        private boolean enabled = true;

        // Getters and Setters
        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getAlgorithm() {
            return algorithm;
        }

        public void setAlgorithm(String algorithm) {
            this.algorithm = algorithm;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }

    /**
     * RSA encryption properties
     */
    public static class RsaProperties {
        @Positive(message = "RSA key size must be positive")
        private int keySize = 1024;
        
        @NotBlank(message = "RSA algorithm cannot be blank")
        private String algorithm = "RSA";
        
        private boolean enabled = true;

        // Getters and Setters
        public int getKeySize() {
            return keySize;
        }

        public void setKeySize(int keySize) {
            this.keySize = keySize;
        }

        public String getAlgorithm() {
            return algorithm;
        }

        public void setAlgorithm(String algorithm) {
            this.algorithm = algorithm;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }
}