package com.chavez.userauth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chavez.userauth.dto.AuthResponse;
import com.chavez.userauth.dto.LoginRequest;
import com.chavez.userauth.dto.RegisterRequest;
import com.chavez.userauth.dto.UserResponse;
import com.chavez.userauth.service.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class AuthController {

    private final UserService userService;

    /**
     * POST /api/auth/register
     * Register a new user
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request, HttpSession session) {
        try {
            UserResponse user = userService.registerUser(request);
            
            // Auto-login the user after registration by setting session
            session.setAttribute("userId", user.getId());
            session.setAttribute("username", user.getUsername());
            
            AuthResponse response = AuthResponse.builder()
                    .success(true)
                    .message("User registered successfully")
                    .user(user)
                    .build();
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            AuthResponse response = AuthResponse.builder()
                    .success(false)
                    .message(e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            AuthResponse response = AuthResponse.builder()
                    .success(false)
                    .message("Registration failed: " + e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * POST /api/auth/login
     * Login with username and password
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request, HttpSession session) {
        try {
            UserResponse user = userService.authenticateUser(request.getUsername(), request.getPassword());
            
            // Store user ID in session
            session.setAttribute("userId", user.getId());
            session.setAttribute("username", user.getUsername());
            
            AuthResponse response = AuthResponse.builder()
                    .success(true)
                    .message("User logged in successfully")
                    .user(user)
                    .build();
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            AuthResponse response = AuthResponse.builder()
                    .success(false)
                    .message(e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            AuthResponse response = AuthResponse.builder()
                    .success(false)
                    .message("Login failed: " + e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * GET /api/user/me
     * Get current authenticated user (protected endpoint)
     */
    @GetMapping("/me")
    public ResponseEntity<AuthResponse> getCurrentUser(HttpSession session) {
        try {
            Long userId = (Long) session.getAttribute("userId");
            
            if (userId == null) {
                AuthResponse response = AuthResponse.builder()
                        .success(false)
                        .message("User not authenticated")
                        .build();
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
            UserResponse user = userService.getUserById(userId);
            AuthResponse response = AuthResponse.builder()
                    .success(true)
                    .message("User retrieved successfully")
                    .user(user)
                    .build();
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            AuthResponse response = AuthResponse.builder()
                    .success(false)
                    .message(e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    /**
     * POST /api/auth/logout
     * Logout the current user
     */
    @PostMapping("/logout")
    public ResponseEntity<AuthResponse> logout(HttpSession session) {
        session.invalidate();
        AuthResponse response = AuthResponse.builder()
                .success(true)
                .message("User logged out successfully")
                .build();
        return ResponseEntity.ok(response);
    }
}
