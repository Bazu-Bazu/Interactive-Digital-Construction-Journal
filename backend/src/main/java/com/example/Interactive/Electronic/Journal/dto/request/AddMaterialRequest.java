package com.example.Interactive.Electronic.Journal.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddMaterialRequest {

    @NotBlank
    private String name;

    private Integer cost;

    @NotBlank
    private Integer amount;

    private String description;

    @NotBlank
    private Long objectId;

}
