package com.example.Interactive.Electronic.Journal.controller;

import com.example.Interactive.Electronic.Journal.dto.request.AddChangeRequest;
import com.example.Interactive.Electronic.Journal.dto.response.ChangeResponse;
import com.example.Interactive.Electronic.Journal.service.ChangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PatchMapping("/accept")
    public ResponseEntity<ChangeResponse> acceptChange(@RequestParam Long changeId) {
        ChangeResponse response = changeService.acceptChange(changeId);

        return ResponseEntity.ok(response);
    }

}
