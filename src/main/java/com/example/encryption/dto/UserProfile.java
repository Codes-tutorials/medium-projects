package com.example.encryption.dto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * User profile DTO
 */
public class UserProfile {

    private String userId;
    private String username;
    private String email;
    private String phoneNumber;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;
    private List<String> roles;
    private UserPreferences preferences;

    // Constructors
    public UserProfile() {
        this.createdAt = LocalDateTime.now();
        this.status = "ACTIVE";
    }

    public UserProfile(String username, String phoneNumber) {
        this();
        this.username = username;
        this.phoneNumber = phoneNumber;
    }

    public UserProfile(String userId, String username, String email, String phoneNumber) {
        this();
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(LocalDateTime lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public UserPreferences getPreferences() {
        return preferences;
    }

    public void setPreferences(UserPreferences preferences) {
        this.preferences = preferences;
    }

    /**
     * Nested class for user preferences
     */
    public static class UserPreferences {
        private String language;
        private String timezone;
        private boolean emailNotifications;
        private boolean smsNotifications;

        // Constructors
        public UserPreferences() {}

        public UserPreferences(String language, String timezone, boolean emailNotifications, boolean smsNotifications) {
            this.language = language;
            this.timezone = timezone;
            this.emailNotifications = emailNotifications;
            this.smsNotifications = smsNotifications;
        }

        // Getters and Setters
        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getTimezone() {
            return timezone;
        }

        public void setTimezone(String timezone) {
            this.timezone = timezone;
        }

        public boolean isEmailNotifications() {
            return emailNotifications;
        }

        public void setEmailNotifications(boolean emailNotifications) {
            this.emailNotifications = emailNotifications;
        }

        public boolean isSmsNotifications() {
            return smsNotifications;
        }

        public void setSmsNotifications(boolean smsNotifications) {
            this.smsNotifications = smsNotifications;
        }
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                ", lastLoginAt=" + lastLoginAt +
                ", roles=" + roles +
                ", preferences=" + preferences +
                '}';
    }
}