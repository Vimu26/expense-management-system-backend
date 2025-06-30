package com.expense.auth;

import com.expense.auth.dto.*;
import com.expense.config.JwtUtil;
import com.expense.user.User;
import com.expense.user.UserRepository;
import com.expense.user.dto.UserResponse;
import com.expense.user.enums.UserRoles;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserResponse register(RegisterRequest request) {
        // Check if email exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use");
        }

        // Check if username exists
        if (userRepository.findByUserName(request.getUserName()).isPresent()) {
            throw new RuntimeException("Username already in use");
        }

        // Create and save user
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .userName(request.getUserName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRoles.ROLE_USER)
                .build();

        User savedUser = userRepository.save(user);
//        String token = jwtUtil.generateToken(user.getEmail());
        //send saved user
        return new UserResponse(savedUser);
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user);
        return new AuthResponse(token, new UserResponse(user));
    }

    public User getLoggedInUserByEmail(String token) {
        String email = jwtUtil.extractEmail(token);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User getLoggedInUserById(String token) {
        Long userId = jwtUtil.extractUserId(token);
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
