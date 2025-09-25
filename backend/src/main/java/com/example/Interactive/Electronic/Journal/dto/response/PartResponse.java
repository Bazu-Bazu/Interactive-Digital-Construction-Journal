package com.example.Interactive.Electronic.Journal.dto.response;

import lombok.Builder;
import lombok.Data;

import javax.swing.text.StyledEditorKit;
import java.time.LocalDate;

@Data
@Builder
public class PartResponse {

    private Long id;
    private Long objectId;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean done;

}
