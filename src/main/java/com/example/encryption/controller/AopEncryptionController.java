package com.example.encryption.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson2.JSON;
import com.example.encryption.annotation.Decrypt;
import com.example.encryption.annotation.Encrypt;
import com.example.encryption.dto.UserProfile;
import com.example.encryption.dto.UserRegisterRequest;
import com.example.encryption.dto.UserRegisterResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controller demonstrating AOP-based encryption/decryption approach
 * 
 * This controller uses custom annotations (@Encrypt and @Decrypt) with AOP
 * to automatically handle encryption and decryption of request/response data.
 */
@RestController
@RequestMapping("/api/aop")
@Tag(name = "AOP Encryption", description = "Demonstrates AOP-based transparent encryption/decryption")
public class AopEncryptionController {

    private static final Logger logger = LoggerFactory.getLogger(AopEncryptionController.class);

    @PostMapping("/register")
    @Decrypt(throwOnFailure = true, parameterIndex = 0)
    @Operation(
        summary = "Register user with encrypted data",
        description = "Accepts encrypted user registration data and returns success response",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                examples = @ExampleObject(
                    name = "Encrypted Registration Data",
                    value = "\"base64_encrypted_json_data_here\"",
                    description = "Base64 encoded AES encrypted JSON containing user registration data"
                )
            )
        )
    )
    @ApiResponse(responseCode = "200", description = "User registered successfully")
    @ApiResponse(responseCode = "400", description = "Invalid encrypted data or validation error")
    public ResponseEntity<UserRegisterResponse> register(
            @Parameter(description = "Base64 encoded encrypted user registration data")
            @RequestBody String encryptedData) {
        
        logger.info("Processing user registration with encrypted data");

        try {
            // At this point, the data is already decrypted by the @Decrypt aspect
            UserRegisterRequest request = JSON.parseObject(encryptedData, UserRegisterRequest.class);
            
            logger.debug("Parsed registration request for user: {}", request.getUserId());

            // Simulate business logic
            String token = generateToken(request.getUserId());
            UserRegisterResponse response = new UserRegisterResponse(
                "User registered successfully", 
                request.getUserId(), 
                true, 
                token
            );

            logger.info("User registration completed successfully for: {}", request.getUserId());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Failed to process user registration", e);
            UserRegisterResponse errorResponse = new UserRegisterResponse(
                "Registration failed: " + e.getMessage(), 
                null, 
                false
            );
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @GetMapping("/profile")
    @Encrypt(algorithm = "AES", encryptEntireResponse = true)
    @Operation(
        summary = "Get user profile (encrypted response)",
        description = "Returns user profile data encrypted using AES algorithm"
    )
    @ApiResponse(responseCode = "200", description = "User profile retrieved and encrypted successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity<UserProfile> getProfile(
            @Parameter(description = "User ID to retrieve profile for")
            @RequestParam String userId) {
        
        logger.info("Retrieving user profile for: {}", userId);

        try {
            // Simulate fetching user profile from database
            UserProfile profile = createSampleUserProfile(userId);
            
            logger.debug("Retrieved profile for user: {}", userId);
            
            // The response will be automatically encrypted by the @Encrypt aspect
            return ResponseEntity.ok(profile);

        } catch (Exception e) {
            logger.error("Failed to retrieve user profile for: {}", userId, e);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/update-profile")
    @Decrypt(throwOnFailure = true, parameterIndex = 0)
    @Encrypt(algorithm = "AES")
    @Operation(
        summary = "Update user profile with encryption",
        description = "Accepts encrypted profile data and returns encrypted response"
    )
    @ApiResponse(responseCode = "200", description = "Profile updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid encrypted data")
    public ResponseEntity<UserProfile> updateProfile(
            @Parameter(description = "Base64 encoded encrypted user profile data")
            @RequestBody String encryptedProfileData) {
        
        logger.info("Processing profile update with encrypted data");

        try {
            // Data is decrypted by @Decrypt aspect
            UserProfile profileUpdate = JSON.parseObject(encryptedProfileData, UserProfile.class);
            
            logger.debug("Parsed profile update for user: {}", profileUpdate.getUserId());

            // Simulate profile update logic
            UserProfile updatedProfile = updateUserProfile(profileUpdate);
            
            logger.info("Profile updated successfully for user: {}", profileUpdate.getUserId());
            
            // Response will be encrypted by @Encrypt aspect
            return ResponseEntity.ok(updatedProfile);

        } catch (Exception e) {
            logger.error("Failed to update user profile", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/users")
    @Encrypt(algorithm = "AES")
    @Operation(
        summary = "Get all users (encrypted response)",
        description = "Returns list of all users with encrypted response"
    )
    @ApiResponse(responseCode = "200", description = "Users list retrieved and encrypted successfully")
    public ResponseEntity<List<UserProfile>> getAllUsers() {
        logger.info("Retrieving all users");

        try {
            // Simulate fetching all users
            List<UserProfile> users = List.of(
                createSampleUserProfile("user1"),
                createSampleUserProfile("user2"),
                createSampleUserProfile("user3")
            );

            logger.debug("Retrieved {} users", users.size());
            return ResponseEntity.ok(users);

        } catch (Exception e) {
            logger.error("Failed to retrieve users", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    // Helper methods
    private String generateToken(String userId) {
        return "token_" + userId + "_" + System.currentTimeMillis();
    }

    private UserProfile createSampleUserProfile(String userId) {
        UserProfile profile = new UserProfile(userId, "User " + userId, userId + "@example.com", "138****1234");
        profile.setRoles(List.of("USER", "CUSTOMER"));
        
        UserProfile.UserPreferences preferences = new UserProfile.UserPreferences(
            "en", "UTC", true, false
        );
        profile.setPreferences(preferences);
        
        return profile;
    }

    private UserProfile updateUserProfile(UserProfile profileUpdate) {
        // Simulate profile update logic
        profileUpdate.setStatus("UPDATED");
        return profileUpdate;
    }
}