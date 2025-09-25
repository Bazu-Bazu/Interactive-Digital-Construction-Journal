package com.example.Interactive.Electronic.Journal.controller;

import com.example.Interactive.Electronic.Journal.dto.request.AddRemarkRequest;
import com.example.Interactive.Electronic.Journal.dto.response.RemarkResponse;
import com.example.Interactive.Electronic.Journal.service.RemarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/remark")
@RequiredArgsConstructor
public class RemarkController {

    private final RemarkService remarkService;

    @PostMapping("/add")
    public ResponseEntity<RemarkResponse> addRemark(@RequestBody AddRemarkRequest request) {
        RemarkResponse response = remarkService.addRemark(request);

        return ResponseEntity.ok(response);
    }

}
