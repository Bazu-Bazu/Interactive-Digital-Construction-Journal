package com.example.Interactive.Electronic.Journal.controller;

import com.example.Interactive.Electronic.Journal.dto.request.LoginUserRequest;
import com.example.Interactive.Electronic.Journal.dto.request.RegisterUserRequest;
import com.example.Interactive.Electronic.Journal.dto.request.UpdateAccessTokenRequest;
import com.example.Interactive.Electronic.Journal.dto.response.UserResponse;
import com.example.Interactive.Electronic.Journal.exception.InvalidTokenException;
import com.example.Interactive.Electronic.Journal.service.AuthService;
import com.example.Interactive.Electronic.Journal.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> registerUser(@RequestBody RegisterUserRequest request) {
        UserResponse response = authService.signUp(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> authenticate(@RequestBody LoginUserRequest request) {
        UserResponse response = authService.authenticate(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/update-token")
    public ResponseEntity<UserResponse> updateToken(@RequestBody UpdateAccessTokenRequest request)
    {
        UserResponse response = authService.updateAccessToken(request.getRefreshToken());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public void logout(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new InvalidTokenException("Invalid authorization header.");
        }

        String accessToken = authHeader.substring(7);
        String email = jwtService.extractUsername(accessToken);

        authService.logout(email);
    }

}
