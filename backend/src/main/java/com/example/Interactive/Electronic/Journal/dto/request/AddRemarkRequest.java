package com.example.Interactive.Electronic.Journal.dto.request;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class AddRemarkRequest {

    private Long objectId;
    private String description;
    private LocalDate deadline;
    private List<Double> coordinates;

}
