package com.example.encryption.dto;

import java.time.LocalDateTime;

/**
 * User registration response DTO
 */
public class UserRegisterResponse {

    private String message;
    private String userId;
    private boolean success;
    private LocalDateTime timestamp;
    private String token;

    // Constructors
    public UserRegisterResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public UserRegisterResponse(String message, String userId) {
        this();
        this.message = message;
        this.userId = userId;
        this.success = true;
    }

    public UserRegisterResponse(String message, String userId, boolean success) {
        this();
        this.message = message;
        this.userId = userId;
        this.success = success;
    }

    public UserRegisterResponse(String message, String userId, boolean success, String token) {
        this();
        this.message = message;
        this.userId = userId;
        this.success = success;
        this.token = token;
    }

    // Getters and Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "UserRegisterResponse{" +
                "message='" + message + '\'' +
                ", userId='" + userId + '\'' +
                ", success=" + success +
                ", timestamp=" + timestamp +
                ", token='" + (token != null ? "[PROTECTED]" : null) + '\'' +
                '}';
    }
}