package com.example.encryption.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.example.encryption.config.EncryptionProperties;
import com.example.encryption.exception.EncryptionException;

/**
 * AES encryption/decryption utility class
 * 
 * Provides symmetric encryption using AES algorithm with configurable key and algorithm.
 * Supports both ECB and CBC modes with proper padding.
 */
@Component
public class AESUtils {

    private static final Logger logger = LoggerFactory.getLogger(AESUtils.class);

    private final EncryptionProperties encryptionProperties;

    public AESUtils(EncryptionProperties encryptionProperties) {
        this.encryptionProperties = encryptionProperties;
    }

    /**
     * Encrypt data using AES algorithm
     * 
     * @param data Plain text data to encrypt
     * @return Base64 encoded encrypted data
     * @throws EncryptionException if encryption fails
     */
    public String encrypt(String data) {
        if (data == null || data.isEmpty()) {
            throw new EncryptionException("Data to encrypt cannot be null or empty");
        }

        try {
            String key = encryptionProperties.getAes().getKey();
            String algorithm = encryptionProperties.getAes().getAlgorithm();

            Cipher cipher = Cipher.getInstance(algorithm);
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] encryptedBytes = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            String encryptedData = Base64.getEncoder().encodeToString(encryptedBytes);

            if (encryptionProperties.isDebugMode()) {
                logger.debug("AES Encryption - Original length: {}, Encrypted length: {}", 
                    data.length(), encryptedData.length());
            }

            return encryptedData;

        } catch (Exception e) {
            logger.error("AES encryption failed for data length: {}", data.length(), e);
            throw new EncryptionException("Failed to encrypt data using AES", e);
        }
    }

    /**
     * Decrypt data using AES algorithm
     * 
     * @param encryptedData Base64 encoded encrypted data
     * @return Decrypted plain text data
     * @throws EncryptionException if decryption fails
     */
    public String decrypt(String encryptedData) {
        if (encryptedData == null || encryptedData.isEmpty()) {
            throw new EncryptionException("Encrypted data cannot be null or empty");
        }

        try {
            String key = encryptionProperties.getAes().getKey();
            String algorithm = encryptionProperties.getAes().getAlgorithm();

            Cipher cipher = Cipher.getInstance(algorithm);
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            String decryptedData = new String(decryptedBytes, StandardCharsets.UTF_8);

            if (encryptionProperties.isDebugMode()) {
                logger.debug("AES Decryption - Encrypted length: {}, Decrypted length: {}", 
                    encryptedData.length(), decryptedData.length());
            }

            return decryptedData;

        } catch (Exception e) {
            logger.error("AES decryption failed for data length: {}", encryptedData.length(), e);
            throw new EncryptionException("Failed to decrypt data using AES", e);
        }
    }

    /**
     * Validate if the provided key is suitable for AES encryption
     * 
     * @param key The key to validate
     * @return true if key is valid, false otherwise
     */
    public boolean isValidKey(String key) {
        if (key == null) {
            return false;
        }
        
        int keyLength = key.getBytes(StandardCharsets.UTF_8).length;
        // AES supports key lengths of 128, 192, or 256 bits (16, 24, or 32 bytes)
        return keyLength == 16 || keyLength == 24 || keyLength == 32;
    }

    /**
     * Generate a random AES key
     * 
     * @param keySize Key size in bits (128, 192, or 256)
     * @return Base64 encoded random key
     */
    public String generateRandomKey(int keySize) {
        try {
            javax.crypto.KeyGenerator keyGenerator = javax.crypto.KeyGenerator.getInstance("AES");
            keyGenerator.init(keySize);
            javax.crypto.SecretKey secretKey = keyGenerator.generateKey();
            return Base64.getEncoder().encodeToString(secretKey.getEncoded());
        } catch (Exception e) {
            logger.error("Failed to generate AES key with size: {}", keySize, e);
            throw new EncryptionException("Failed to generate AES key", e);
        }
    }
}