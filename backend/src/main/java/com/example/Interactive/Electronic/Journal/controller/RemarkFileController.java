package com.example.Interactive.Electronic.Journal.controller;

import com.example.Interactive.Electronic.Journal.dto.response.RemarkFileResponse;
import com.example.Interactive.Electronic.Journal.entity.Remark;
import com.example.Interactive.Electronic.Journal.exception.InvalidTokenException;
import com.example.Interactive.Electronic.Journal.exception.RemarkException;
import com.example.Interactive.Electronic.Journal.repository.RemarkRepository;
import com.example.Interactive.Electronic.Journal.service.RemarkFileService;
import com.example.Interactive.Electronic.Journal.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/remark-files")
@RequiredArgsConstructor
public class RemarkFileController {

    private final RemarkFileService remarkFileService;
    private final JwtService jwtService;
    private final RemarkRepository remarkRepository;

    @PostMapping("/add")
    public ResponseEntity<List<RemarkFileResponse>> addRemarksFiles(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam List<MultipartFile> files,
            @RequestParam Long remarkId)
    {
        Remark remark = remarkRepository.findById(remarkId)
                .orElseThrow(() -> new RemarkException("Remark not found."));

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new InvalidTokenException("Invalid authorization header.");
        }

        String token = authHeader.substring(7);
        Long accessibleObjectId = jwtService.getAccessibleObjectId(token);
        if (!Objects.equals(accessibleObjectId, remark.getObject().getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        List<RemarkFileResponse> response = remarkFileService.addRemarkFiles(files, remarkId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-all-by-remark")
    public ResponseEntity<List<RemarkFileResponse>> getAllFilesOfRemark(@RequestParam Long remarkId) {
        List<RemarkFileResponse> response = remarkFileService.getAllFilesOfRemark(remarkId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
        return remarkFileService.downloadFile(fileId);
    }

}
