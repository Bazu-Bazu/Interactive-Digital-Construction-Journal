package com.example.Interactive.Electronic.Journal.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class RemarkResponse {

    private Long id;
    private LocalDateTime createdAt;
    private String description;
    private List<Double> coordinates;
    private LocalDate deadline;
    private Boolean fixed;
    private List<String> urls;

}
