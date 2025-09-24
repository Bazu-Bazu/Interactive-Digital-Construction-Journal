package com.example.Interactive.Electronic.Journal.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AddPartRequest {

    @NotBlank
    private Long objectId;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private LocalDate startDate;

    @NotBlank
    private LocalDate endDate;

    @NotBlank
    private Boolean done;

}
