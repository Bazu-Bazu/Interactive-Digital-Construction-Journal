package com.example.Interactive.Electronic.Journal.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateAccessTokenRequest {

    @NotBlank
    private String refreshToken;

}
