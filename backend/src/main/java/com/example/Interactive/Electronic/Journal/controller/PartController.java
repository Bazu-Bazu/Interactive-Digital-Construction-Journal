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
<<<<<<< HEAD
    public ResponseEntity<PartResponse> addPart(AddPartRequest request) {
        PartResponse response = partService.addPart(request);

        return ResponseEntity.ok(response);
=======
    public ResponseEntity<List<PartResponse>> addPart(@RequestBody List<AddPartRequest> requests) {
        List<PartResponse> responses = partService.addPart(requests);

        return ResponseEntity.ok(responses);
>>>>>>> backend
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
