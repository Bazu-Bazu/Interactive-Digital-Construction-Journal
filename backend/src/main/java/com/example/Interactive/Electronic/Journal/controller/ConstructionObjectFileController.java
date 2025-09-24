package com.example.Interactive.Electronic.Journal.controller;

import com.example.Interactive.Electronic.Journal.entity.ConstructionObjectFile;
import org.springframework.core.io.Resource;
import com.example.Interactive.Electronic.Journal.dto.response.ObjectFileResponse;
import com.example.Interactive.Electronic.Journal.service.ConstructionObjectFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/object-file")
@RequiredArgsConstructor
public class ConstructionObjectFileController {

    private final ConstructionObjectFileService constructionObjectFileService;

    @PostMapping("/add")
    public ResponseEntity<ObjectFileResponse> addFile(
            @RequestParam("file") MultipartFile file,
            Long objectId) throws IOException
    {
        ObjectFileResponse response = constructionObjectFileService.addFile(file, objectId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-by-url")
    public ResponseEntity<Resource> getFileByUrl(@RequestParam String url) {
        GridFsResource resource = constructionObjectFileService.getFileByUrl(url);
        ConstructionObjectFile metadata = constructionObjectFileService.getFileMetadataByUrl(url);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(metadata.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + metadata.getFileName() + "\"")
                .contentLength(metadata.getSize())
                .body(resource);
    }

}
