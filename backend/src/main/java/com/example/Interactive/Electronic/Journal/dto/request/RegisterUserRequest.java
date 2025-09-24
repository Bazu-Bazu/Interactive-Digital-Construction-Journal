package com.example.Interactive.Electronic.Journal.dto.request;

<<<<<<< HEAD
=======
import com.example.Interactive.Electronic.Journal.enums.Role;
>>>>>>> backend
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
<<<<<<< HEAD
public class RegisterUserDto {
=======
public class RegisterUserRequest {
>>>>>>> backend

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

<<<<<<< HEAD
    @NotBlank
=======
>>>>>>> backend
    private String patronymic;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

<<<<<<< HEAD
=======
    @NotBlank
    private Role role;

>>>>>>> backend
}
