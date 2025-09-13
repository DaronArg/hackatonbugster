package com.ganatan.backend_java.modules.users.user.login;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for user login requests.
 * This class encapsulates the data required for a user to log in,
 * including the user's email and password.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    /**
     * The email of the user attempting to log in.
     */
    @NotBlank(message = "Email must be not empty.")
    @Email(message = "Email must be email format.")
    private String email;

    /**
     * The password of the user attempting to log in.
     */
    @NotBlank(message = "Password must be not empty.")
    private String password;
}
