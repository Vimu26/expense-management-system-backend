package com.expense.auth.dto;

import com.expense.user.dto.UserResponse;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {
    private String token;
    private UserResponse user;
}
