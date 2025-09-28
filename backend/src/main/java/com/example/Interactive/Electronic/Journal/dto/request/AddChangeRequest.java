package com.example.Interactive.Electronic.Journal.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AddChangeRequest {

    @NotBlank
    private String description;

    private String type;

    private LocalDate proposedStartDate;

    private LocalDate proposedEndDate;

    @NotBlank
    private Long partId;

    @NotBlank
    private Long objectId;

}