package com.example.Interactive.Electronic.Journal.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MaterialResponse {

    private Long id;
    private String name;
    private Integer cost;
    private Integer amount;
    private String description;
    private Long objectId;

}
