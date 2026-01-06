package com.example.encryption.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson2.JSON;
import com.example.encryption.annotation.Decrypt;
import com.example.encryption.annotation.Encrypt;
import com.example.encryption.exception.EncryptionException;
import com.example.encryption.util.AESUtils;

/**
 * AOP Aspect for handling encryption and decryption of method parameters and return values
 * 
 * This aspect intercepts methods annotated with @Encrypt and @Decrypt annotations
 * and automatically handles the encryption/decryption process.
 */
@Aspect
@Component
public class DataEncryptAspect {

    private static final Logger logger = LoggerFactory.getLogger(DataEncryptAspect.class);

    private final AESUtils aesUtils;

    public DataEncryptAspect(AESUtils aesUtils) {
        this.aesUtils = aesUtils;
    }

    /**
     * Around advice for methods annotated with @Encrypt
     * Encrypts the method return value
     */
    @Around("@annotation(encrypt)")
    public Object encryptAround(ProceedingJoinPoint joinPoint, Encrypt encrypt) throws Throwable {
        logger.debug("Encrypting response for method: {}", joinPoint.getSignature().getName());

        try {
            // Execute the original method
            Object result = joinPoint.proceed();
            
            if (result == null) {
                logger.debug("Method returned null, skipping encryption");
                return null;
            }

            // Convert result to JSON and encrypt
            String jsonResult = JSON.toJSONString(result);
            String encryptedResult = aesUtils.encrypt(jsonResult);

            logger.debug("Successfully encrypted response for method: {}", joinPoint.getSignature().getName());
            return encryptedResult;

        } catch (Exception e) {
            logger.error("Failed to encrypt response for method: {}", joinPoint.getSignature().getName(), e);
            throw new EncryptionException("Failed to encrypt method response", e);
        }
    }

    /**
     * Around advice for methods annotated with @Decrypt
     * Decrypts the method parameters
     */
    @Around("@annotation(decrypt)")
    public Object decryptAround(ProceedingJoinPoint joinPoint, Decrypt decrypt) throws Throwable {
        logger.debug("Decrypting request for method: {}", joinPoint.getSignature().getName());

        try {
            Object[] args = joinPoint.getArgs();
            
            if (args == null || args.length == 0) {
                logger.debug("No arguments to decrypt, proceeding with original method");
                return joinPoint.proceed();
            }

            // Decrypt the specified parameter (default is first parameter)
            int paramIndex = decrypt.parameterIndex();
            if (paramIndex == -1) {
                // Decrypt all string parameters
                for (int i = 0; i < args.length; i++) {
                    if (args[i] instanceof String) {
                        args[i] = decryptParameter((String) args[i], decrypt);
                    }
                }
            } else if (paramIndex < args.length && args[paramIndex] instanceof String) {
                args[paramIndex] = decryptParameter((String) args[paramIndex], decrypt);
            }

            logger.debug("Successfully decrypted request for method: {}", joinPoint.getSignature().getName());
            return joinPoint.proceed(args);

        } catch (Exception e) {
            logger.error("Failed to decrypt request for method: {}", joinPoint.getSignature().getName(), e);
            
            if (decrypt.throwOnFailure()) {
                throw new EncryptionException("Failed to decrypt method parameters", e);
            } else {
                logger.warn("Continuing with original parameters due to throwOnFailure=false");
                return joinPoint.proceed();
            }
        }
    }

    /**
     * Decrypt a single parameter
     */
    private String decryptParameter(String encryptedParam, Decrypt decrypt) {
        try {
            return aesUtils.decrypt(encryptedParam);
        } catch (Exception e) {
            if (decrypt.throwOnFailure()) {
                throw new EncryptionException("Failed to decrypt parameter", e);
            } else {
                logger.warn("Failed to decrypt parameter, returning original value", e);
                return encryptedParam;
            }
        }
    }
}