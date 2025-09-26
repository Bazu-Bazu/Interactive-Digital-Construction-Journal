package com.example.Interactive.Electronic.Journal.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SupervisionResponse {

    private Long id;
    private String name;
    private List<Long> inspectorIds;
    private List<Long> customerIds;

}
