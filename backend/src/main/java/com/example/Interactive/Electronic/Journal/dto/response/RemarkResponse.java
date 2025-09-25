package com.example.Interactive.Electronic.Journal.dto.response;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class RemarkResponse {

    private Long id;
    private LocalDateTime createdAt;
    private String description;
    private List<Double> coordinates;
    private Boolean fixed;
    private List<String> urls;

}
