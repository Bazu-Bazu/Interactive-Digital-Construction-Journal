package com.example.Interactive.Electronic.Journal.controller;

import com.example.Interactive.Electronic.Journal.dto.request.AddChangeRequest;
import com.example.Interactive.Electronic.Journal.dto.response.ChangeResponse;
import com.example.Interactive.Electronic.Journal.service.ChangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/change")
@RequiredArgsConstructor
public class ChangeController {

    private final ChangeService changeService;

    @PostMapping("/add")
    public ResponseEntity<ChangeResponse> addChange(@RequestBody AddChangeRequest request) {
        ChangeResponse response = changeService.addChange(request);

        return ResponseEntity.ok(response);
    }

}
