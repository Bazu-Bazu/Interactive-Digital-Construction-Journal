package com.example.Interactive.Electronic.Journal.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ObjectFileResponse {

    private String id;
    private String name;
    private String url;
    private String contentType;
    private Long size;
    private Long objectId;

}
