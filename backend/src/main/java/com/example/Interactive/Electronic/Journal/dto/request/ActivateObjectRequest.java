package com.example.Interactive.Electronic.Journal.dto.request;

import lombok.Data;

@Data
public class ActivateObjectRequest {

    private Long foremanId;
    private Long objectId;
    private Boolean activate;

}
