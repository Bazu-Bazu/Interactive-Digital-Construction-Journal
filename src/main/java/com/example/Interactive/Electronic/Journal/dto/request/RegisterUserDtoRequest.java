package com.example.Interactive.Electronic.Journal.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterUserDto {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String patronymic;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

}
