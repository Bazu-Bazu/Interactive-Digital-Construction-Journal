package com.example.Interactive.Electronic.Journal.service;

import com.example.Interactive.Electronic.Journal.dto.request.RegisterUserRequest;
import com.example.Interactive.Electronic.Journal.dto.response.UserResponse;
import com.example.Interactive.Electronic.Journal.entity.User;
import com.example.Interactive.Electronic.Journal.exception.InvalidPasswordException;
import com.example.Interactive.Electronic.Journal.exception.UserAlreadyExistsException;
import com.example.Interactive.Electronic.Journal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse createUser(RegisterUserRequest request) {
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());

        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException("User with this email already exists.");
        }

        if (request.getPassword().length() < 8) {
            throw new InvalidPasswordException("Invalid password length.");
        }

        User newUser = new User();
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setFirstName(request.getFirstName());
        newUser.setLastName(request.getLastName());
        newUser.setPatronymic(request.getPatronymic());
        newUser.setEnabled(true);
        newUser.setRole(request.getRole());
        userRepository.save(newUser);

        return buildUserResponse(newUser);
    }

    private UserResponse buildUserResponse(User user) {
        Long supervisionId;
        if (user.getCustomerSupervision() != null) {
            supervisionId = user.getCustomerSupervision().getId();
        }
        else if (user.getInspectorSupervision() != null) {
            supervisionId = user.getInspectorSupervision().getId();
        }
        else {
            supervisionId = null;
        }

        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .patronymic(user.getPatronymic())
                .role(user.getRole())
                .supervisionId(supervisionId)
                .build();
    }

}
