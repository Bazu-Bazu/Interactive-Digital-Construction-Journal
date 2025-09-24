package com.example.Interactive.Electronic.Journal.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class AddObjectRequest {

    @NotBlank
    private String name;

    @NotBlank
    private List<Double> coordinates;

    @NotBlank
    private LocalDate startDate;

}
