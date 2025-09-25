package com.example.Interactive.Electronic.Journal.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "remark_files")
@Data
public class RemarkFile {

    @Id
    private String id;

    @Field(name = "remark_id")
    private Long remarkId;

    @Field(name = "file_name")
    private String fileName;

    @Field(name = "content_type")
    private String contentType;

    private Long size;

    private String url;

}
