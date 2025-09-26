package com.example.Interactive.Electronic.Journal.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddSupervisionRequest {

    @NotBlank
    private String name;

}
