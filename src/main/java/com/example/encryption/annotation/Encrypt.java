package com.example.encryption.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark methods that should encrypt their response data
 * 
 * When applied to a controller method, the response will be automatically
 * encrypted using the configured encryption algorithm.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Encrypt {
    
    /**
     * Fields to exclude from encryption
     * @return array of field names to exclude
     */
    String[] excludeFields() default {};
    
    /**
     * Encryption algorithm to use
     * @return encryption algorithm (AES or RSA)
     */
    String algorithm() default "AES";
    
    /**
     * Whether to encrypt the entire response or just specific fields
     * @return true to encrypt entire response, false to encrypt only specified fields
     */
    boolean encryptEntireResponse() default true;
}