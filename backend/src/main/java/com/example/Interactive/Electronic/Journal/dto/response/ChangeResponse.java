package com.example.Interactive.Electronic.Journal.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ChangeResponse {

    private Long id;
    private String description;
    private Boolean accepted;
    private String type;
    private LocalDate proposedStartDate;
    private LocalDate proposedEndDate;
    private Long partId;
    private Long objectId;

}
