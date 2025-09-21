package com.example.Interactive.Electronic.Journal.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "contraction_object_files")
@Data
public class ConstructionObjectFile {

    @Id
    private String id;

    @Field(name = "object_id")
    private Long objectId;

    @Field(name = "file_name")
    private String fileName;

    @Field(name = "content_type")
    private String contentType;

    private Long size;

    private String url;

}
