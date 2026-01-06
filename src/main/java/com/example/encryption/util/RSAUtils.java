package com.example.encryption.util;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.example.encryption.config.EncryptionProperties;
import com.example.encryption.exception.EncryptionException;

/**
 * RSA encryption/decryption utility class
 * 
 * Provides asymmetric encryption using RSA algorithm with configurable key size.
 * Supports key pair generation, public key encryption, and private key decryption.
 */
@Component
public class RSAUtils {

    private static final Logger logger = LoggerFactory.getLogger(RSAUtils.class);

    private final EncryptionProperties encryptionProperties;

    public RSAUtils(EncryptionProperties encryptionProperties) {
        this.encryptionProperties = encryptionProperties;
    }

    /**
     * Generate RSA key pair
     * 
     * @return Map containing base64 encoded public and private keys
     * @throws EncryptionException if key generation fails
     */
    public Map<String, String> generateKeyPair() {
        try {
            int keySize = encryptionProperties.getRsa().getKeySize();
            String algorithm = encryptionProperties.getRsa().getAlgorithm();

            KeyPairGenerator generator = KeyPairGenerator.getInstance(algorithm);
            generator.initialize(keySize);
            KeyPair keyPair = generator.generateKeyPair();

            String publicKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
            String privateKey = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());

            Map<String, String> keys = new HashMap<>();
            keys.put("publicKey", publicKey);
            keys.put("privateKey", privateKey);

            if (encryptionProperties.isDebugMode()) {
                logger.debug("RSA key pair generated - Key size: {} bits", keySize);
            }

            return keys;

        } catch (Exception e) {
            logger.error("Failed to generate RSA key pair", e);
            throw new EncryptionException("Failed to generate RSA key pair", e);
        }
    }

    /**
     * Encrypt data using RSA public key
     * 
     * @param data Plain text data to encrypt
     * @param publicKeyStr Base64 encoded public key
     * @return Base64 encoded encrypted data
     * @throws EncryptionException if encryption fails
     */
    public String encryptWithPublicKey(String data, String publicKeyStr) {
        if (data == null || data.isEmpty()) {
            throw new EncryptionException("Data to encrypt cannot be null or empty");
        }
        if (publicKeyStr == null || publicKeyStr.isEmpty()) {
            throw new EncryptionException("Public key cannot be null or empty");
        }

        try {
            // Decode the public key
            byte[] keyBytes = Base64.getDecoder().decode(publicKeyStr);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            java.security.KeyFactory keyFactory = java.security.KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(spec);

            // Encrypt the data
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedBytes = cipher.doFinal(data.getBytes());

            String encryptedData = Base64.getEncoder().encodeToString(encryptedBytes);

            if (encryptionProperties.isDebugMode()) {
                logger.debug("RSA Public Key Encryption - Original length: {}, Encrypted length: {}", 
                    data.length(), encryptedData.length());
            }

            return encryptedData;

        } catch (Exception e) {
            logger.error("RSA public key encryption failed", e);
            throw new EncryptionException("Failed to encrypt data using RSA public key", e);
        }
    }

    /**
     * Decrypt data using RSA private key
     * 
     * @param encryptedData Base64 encoded encrypted data
     * @param privateKeyStr Base64 encoded private key
     * @return Decrypted plain text data
     * @throws EncryptionException if decryption fails
     */
    public String decryptWithPrivateKey(String encryptedData, String privateKeyStr) {
        if (encryptedData == null || encryptedData.isEmpty()) {
            throw new EncryptionException("Encrypted data cannot be null or empty");
        }
        if (privateKeyStr == null || privateKeyStr.isEmpty()) {
            throw new EncryptionException("Private key cannot be null or empty");
        }

        try {
            // Decode the private key
            byte[] keyBytes = Base64.getDecoder().decode(privateKeyStr);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            java.security.KeyFactory keyFactory = java.security.KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(spec);

            // Decrypt the data
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));

            String decryptedData = new String(decryptedBytes);

            if (encryptionProperties.isDebugMode()) {
                logger.debug("RSA Private Key Decryption - Encrypted length: {}, Decrypted length: {}", 
                    encryptedData.length(), decryptedData.length());
            }

            return decryptedData;

        } catch (Exception e) {
            logger.error("RSA private key decryption failed", e);
            throw new EncryptionException("Failed to decrypt data using RSA private key", e);
        }
    }

    /**
     * Encrypt data using RSA private key (for digital signatures)
     * 
     * @param data Plain text data to encrypt
     * @param privateKeyStr Base64 encoded private key
     * @return Base64 encoded encrypted data
     * @throws EncryptionException if encryption fails
     */
    public String encryptWithPrivateKey(String data, String privateKeyStr) {
        if (data == null || data.isEmpty()) {
            throw new EncryptionException("Data to encrypt cannot be null or empty");
        }
        if (privateKeyStr == null || privateKeyStr.isEmpty()) {
            throw new EncryptionException("Private key cannot be null or empty");
        }

        try {
            // Decode the private key
            byte[] keyBytes = Base64.getDecoder().decode(privateKeyStr);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            java.security.KeyFactory keyFactory = java.security.KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(spec);

            // Encrypt the data
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] encryptedBytes = cipher.doFinal(data.getBytes());

            return Base64.getEncoder().encodeToString(encryptedBytes);

        } catch (Exception e) {
            logger.error("RSA private key encryption failed", e);
            throw new EncryptionException("Failed to encrypt data using RSA private key", e);
        }
    }

    /**
     * Decrypt data using RSA public key (for digital signature verification)
     * 
     * @param encryptedData Base64 encoded encrypted data
     * @param publicKeyStr Base64 encoded public key
     * @return Decrypted plain text data
     * @throws EncryptionException if decryption fails
     */
    public String decryptWithPublicKey(String encryptedData, String publicKeyStr) {
        if (encryptedData == null || encryptedData.isEmpty()) {
            throw new EncryptionException("Encrypted data cannot be null or empty");
        }
        if (publicKeyStr == null || publicKeyStr.isEmpty()) {
            throw new EncryptionException("Public key cannot be null or empty");
        }

        try {
            // Decode the public key
            byte[] keyBytes = Base64.getDecoder().decode(publicKeyStr);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            java.security.KeyFactory keyFactory = java.security.KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(spec);

            // Decrypt the data
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedData));

            return new String(decryptedBytes);

        } catch (Exception e) {
            logger.error("RSA public key decryption failed", e);
            throw new EncryptionException("Failed to decrypt data using RSA public key", e);
        }
    }
}