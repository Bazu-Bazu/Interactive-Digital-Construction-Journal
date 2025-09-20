package com.example.Interactive.Electronic.Journal.dto.response;

import com.example.Interactive.Electronic.Journal.enums.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {

    private String email;
    private String firstName;
    private String lastName;
    private String patronymic;
    private Role role;
    private String accessToken;
    private String refreshToken;

}