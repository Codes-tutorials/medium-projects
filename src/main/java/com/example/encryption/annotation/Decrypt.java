package com.example.encryption.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark methods that should decrypt their request data
 * 
 * When applied to a controller method, the request parameters will be
 * automatically decrypted using the configured decryption algorithm.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Decrypt {
    
    /**
     * Whether to throw an exception if decryption fails
     * @return true to throw exception on failure, false to continue with original data
     */
    boolean throwOnFailure() default true;
    
    /**
     * Decryption algorithm to use
     * @return decryption algorithm (AES or RSA)
     */
    String algorithm() default "AES";
    
    /**
     * Parameter index to decrypt (for methods with multiple parameters)
     * @return parameter index, -1 for all parameters
     */
    int parameterIndex() default 0;
}