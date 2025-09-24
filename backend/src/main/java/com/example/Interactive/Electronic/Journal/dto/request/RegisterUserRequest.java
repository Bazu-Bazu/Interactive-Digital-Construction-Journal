package com.example.Interactive.Electronic.Journal.dto.request;

import com.example.Interactive.Electronic.Journal.enums.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterUserRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private String patronymic;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private Role role;

}