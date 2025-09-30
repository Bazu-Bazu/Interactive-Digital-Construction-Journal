package com.example.Interactive.Electronic.Journal.controller;

import com.example.Interactive.Electronic.Journal.exception.InvalidTokenException;
import com.example.Interactive.Electronic.Journal.service.jwt.JwtService;
import org.springframework.core.io.Resource;
import com.example.Interactive.Electronic.Journal.dto.response.ObjectFileResponse;
import com.example.Interactive.Electronic.Journal.service.ConstructionObjectFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/object-files")
@RequiredArgsConstructor
public class ConstructionObjectFileController {

    private final ConstructionObjectFileService constructionObjectFileService;
    private final JwtService jwtService;

    @PostMapping("/add")
    public ResponseEntity<ObjectFileResponse> addFile(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam("file") MultipartFile file,
            @RequestParam Long objectId) throws IOException
    {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new InvalidTokenException("Invalid authorization header.");
        }

        String token = authHeader.substring(7);
        Long accessibleObjectId = jwtService.getAccessibleObjectId(token);
        if (!Objects.equals(accessibleObjectId, objectId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        ObjectFileResponse response = constructionObjectFileService.addFile(file, objectId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-all-by-object")
    public ResponseEntity<List<ObjectFileResponse>> getFileByUrl(@RequestParam Long objectId) {
        List<ObjectFileResponse> response = constructionObjectFileService.getFileByObject(objectId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
        return constructionObjectFileService.downloadFile(fileId);
    }


}
