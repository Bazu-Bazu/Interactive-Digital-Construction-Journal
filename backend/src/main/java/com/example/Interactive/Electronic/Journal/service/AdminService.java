package com.example.Interactive.Electronic.Journal.service;

import com.example.Interactive.Electronic.Journal.dto.request.RegisterUserRequest;
import com.example.Interactive.Electronic.Journal.dto.response.UserResponse;
import com.example.Interactive.Electronic.Journal.entity.User;
import com.example.Interactive.Electronic.Journal.enums.Role;
import com.example.Interactive.Electronic.Journal.exception.UserAlreadyExistsException;
import com.example.Interactive.Electronic.Journal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse addAdmin(RegisterUserRequest request) {
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException("User with this email already exists.");
        }

        User admin = new User();
        admin.setEmail(request.getEmail());
        admin.setPassword(passwordEncoder.encode(request.getPassword()));
        admin.setFirstName(request.getFirstName());
        admin.setLastName(request.getLastName());
        admin.setPatronymic(request.getPatronymic());
        admin.setEnabled(true);
        admin.setRole(Role.ROLE_ADMIN);
        userRepository.save(admin);

        return UserResponse.builder()
                .email(admin.getEmail())
                .firstName(admin.getFirstName())
                .lastName(admin.getLastName())
                .patronymic(admin.getPatronymic())
                .role(admin.getRole())
                .build();
    }

}
