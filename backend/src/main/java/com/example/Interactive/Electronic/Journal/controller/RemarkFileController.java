package com.example.Interactive.Electronic.Journal.controller;

import com.example.Interactive.Electronic.Journal.dto.response.RemarkFileResponse;
import com.example.Interactive.Electronic.Journal.service.RemarkFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/remark-files")
@RequiredArgsConstructor
public class RemarkFileController {

    private final RemarkFileService remarkFileService;

    @PostMapping("/add")
    public ResponseEntity<List<RemarkFileResponse>> addRemarksFiles(
            @RequestParam List<MultipartFile> files,
            @RequestParam Long remarkId)
    {
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
