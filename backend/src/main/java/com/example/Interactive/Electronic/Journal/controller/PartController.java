package com.example.Interactive.Electronic.Journal.controller;

import com.example.Interactive.Electronic.Journal.dto.request.AddPartRequest;
import com.example.Interactive.Electronic.Journal.dto.response.PartResponse;
import com.example.Interactive.Electronic.Journal.service.PartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/part")
@RequiredArgsConstructor
public class PartController {

    private final PartService partService;

    @PostMapping("/add")
    public ResponseEntity<PartResponse> addPart(AddPartRequest request) {
        PartResponse response = partService.addPart(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/get")
    public ResponseEntity<PartResponse> getPart(@RequestParam Long partId) {
        PartResponse response = partService.getPart(partId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-all-by-object")
    public ResponseEntity<List<PartResponse>> getAllByObject(@RequestParam Long objectId) {
        List<PartResponse> responses = partService.getAllPartsByObject(objectId);

        return ResponseEntity.ok(responses);
    }

}
