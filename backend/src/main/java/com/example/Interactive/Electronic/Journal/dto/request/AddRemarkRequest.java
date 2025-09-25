package com.example.Interactive.Electronic.Journal.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class AddRemarkRequest {

    private Long objectId;
    private String description;
    private List<Double> coordinates;

}
