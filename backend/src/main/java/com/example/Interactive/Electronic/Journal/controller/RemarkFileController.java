package com.example.Interactive.Electronic.Journal.controller;

import com.example.Interactive.Electronic.Journal.dto.response.RemarkFileResponse;
import com.example.Interactive.Electronic.Journal.service.RemarkFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/remark-file")
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

//    @GetMapping("/get-all-by-remark")
//    public ResponseEntity<List<GridFsResource>> getAllFilesOfRemark(@RequestParam Long remarkId) {
//        List<GridFsResource> response = remarkFileService.getAllFilesOfRemark(remarkId);
//
//        return ResponseEntity.ok(response);
//    }

}
