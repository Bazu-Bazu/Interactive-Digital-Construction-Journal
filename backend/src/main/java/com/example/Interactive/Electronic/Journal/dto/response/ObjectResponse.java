package com.example.Interactive.Electronic.Journal.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class ObjectResponse {

    private Long id;
    private String name;
    private List<Double> coordinates;
    private Long foremanId;
    private Long customerId;
    private Long inspectorId;
    private Boolean activated;
    private LocalDate startDate;

}
